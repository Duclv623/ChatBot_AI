package com.jurisai.service.impl;

import com.jurisai.dao.repository.AccountRepository;
import com.jurisai.dto.request.*;
import com.jurisai.dto.response.ChangePasswordResponseDTO;
import com.jurisai.dto.response.ForgotPasswordResponseDTO;
import com.jurisai.dto.response.LoginResponseDTO;
import com.jurisai.dto.response.VerifyResetPasswordResponseDTO;
import com.jurisai.entity.Account;
import com.jurisai.entity.User;
import com.jurisai.mapper.AccountMapper;
import com.jurisai.service.AccountService;
import com.jurisai.service.JWTService;
import com.jurisai.service.UserService;
import com.jurisai.service.VerifyAccountService;
import com.jurisai.util.AccountConstant;
import com.jurisai.util.SnowflakeIdGenUtils;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final SnowflakeIdGenUtils snowflakeIdGenUtils;
    private final VerifyAccountService verifyAccountService;
    private final JWTService jwtService;
    private final AccountMapper accountMapper;
    private final RedisTemplate<String, String> redisTemplate;


    @Override
    @Transactional
    public Account register(RegisterRequestDTO request) {
        if (accountRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email đã được sử dụng");
        }

        Long accountId = snowflakeIdGenUtils.nextId();
        Account account = accountMapper.toEntity(request);
        account.setId(accountId);
        account.setPassword(passwordEncoder.encode(request.getPassword()));

        userService.createUser(accountId);

        verifyAccountService.generateAndSaveOtp(account.getEmail());

        return accountRepository.save(account);
    }

    // verify new account by otp and email
    @Override
    public void verifyOtp(VerifyAccountRequestDTO request) {
        if(!verifyAccountService.verifyOtp(request.getEmail(), request.getOtp())) {
            throw new BadCredentialsException("Mã OTP không chính xác");
        }

        Account account = accountRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Email hoặc mật khẩu không chính xác."));

        if(account.getIsActived().equals(AccountConstant.IsVerified.VERIFIED.getValue())) {
            throw new IllegalArgumentException("Tài khoản đã được xác thực");
        }

        account.setIsActived(AccountConstant.IsVerified.VERIFIED.getValue());
        account.setUpdatedAt(new Date().getTime());
        accountRepository.save(account);
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        Account account = accountRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Email hoặc mật khẩu không chính xác."));

        if(account == null) {
            throw new BadCredentialsException("Email không tồn tại");
        }

        if(!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new BadCredentialsException("Mật khẩu không chính xác");
        }

        String token;
        // check account is verified otp or not
        if(account.getIsActived().equals(AccountConstant.IsVerified.VERIFIED.getValue())) {
            User user = userService.getUserByAccountId(account.getId());
            token = jwtService.generateToken(account.getId(), user.getId().getUserId(), account.getEmail(),
                    AccountConstant.TokenSubject.USER_ACCESS);
        } else {
            verifyAccountService.generateAndSaveOtp(account.getEmail());
            throw new BadCredentialsException("Bạn cần xác thực tài khoản trước khi đăng nhập");
        }
        return new LoginResponseDTO().builder()
                .token(token)
                .build();
    }

    @Override
    public void logout(Long accountId) {
        jwtService.deleteToken(accountId);

        // delete user profile in cache
        try{
            User user = userService.getUserByAccountId(accountId);
            userService.evictUserProfileCache(user.getId().getUserId());
        } catch(Exception e){
        }
        // send notification to delete access token in cache to gateway
        redisTemplate.convertAndSend("token-invalidation", accountId.toString());
    }

    @Override
    @Transactional
    public ChangePasswordResponseDTO changePassword(Long accountId, ChangePasswordRequestDTO request) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new BadCredentialsException("Tài khoản không tồn tại."));

        if (!passwordEncoder.matches(request.getCurrentPassword(), account.getPassword())) {
            throw new BadCredentialsException("Mật khẩu hiện tại không chính xác.");
        }

        account.setPassword(passwordEncoder.encode(request.getNewPassword()));
        account.setUpdatedAt(new Date().getTime());
        accountRepository.save(account);

        // delete current access token
        jwtService.deleteToken(account.getId());
        // send notification to delete access token in cache to gateway
        redisTemplate.convertAndSend("token-invalidation", accountId.toString());

        User user = userService.getUserByAccountId(account.getId());
        String token = jwtService.generateToken(accountId, user.getId().getUserId(), account.getEmail(),
                AccountConstant.TokenSubject.USER_ACCESS);
        return new ChangePasswordResponseDTO().builder()
                .token(token)
                .build();
    }

    @Override
    public ForgotPasswordResponseDTO forgotPassword(ForgotPasswordRequestDTO request) {
        Account account = accountRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Tài khoản không tồn tại."));
        verifyAccountService.generateAndSaveOtp(account.getEmail());

        User user = userService.getUserByAccountId(account.getId());
        String otpResetToken = jwtService.generateToken(account.getId(), user.getId().getUserId(), account.getEmail(),
                AccountConstant.TokenSubject.OTP_RESET_PASSWORD);
        return new ForgotPasswordResponseDTO().builder()
                .otpResetToken(otpResetToken)
                .build();
    }

    @Override
    public VerifyResetPasswordResponseDTO verifyOtpResetPassword(VerifyResetPasswordRequestDTO request) {
        // validate short-term token and get claimSet
        JWTClaimsSet jwtClaimsSet = jwtService.validateAndGetClaims(request.getOtpResetToken());
        if (jwtClaimsSet == null || !jwtClaimsSet.getSubject().equals(AccountConstant.TokenSubject.OTP_RESET_PASSWORD.toString())) {
            throw new BadCredentialsException("Token xác thực OTP không hợp lệ hoặc đã hết hạn.");
        }

        String email;
        Long accountId, userId;
        try {
            email = jwtClaimsSet.getStringClaim("email");
            accountId = Long.parseLong(jwtClaimsSet.getStringClaim("accountId"));
            userId = Long.parseLong(jwtClaimsSet.getStringClaim("userId"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if(!verifyAccountService.verifyOtp(email, request.getOtp())){
            throw new BadCredentialsException("Mã OTP không chính xác hoặc đã hết hạn.");
        }

        String resetToken = jwtService.generateToken(accountId, userId, email, AccountConstant.TokenSubject.RESET_PASSWORD);

        return new VerifyResetPasswordResponseDTO().builder()
                .resetToken(resetToken)
                .build();
    }

    @Override
    public void resetPassword(ResetPasswordRequestDTO request) {
        JWTClaimsSet jwtClaimsSet = jwtService.validateAndGetClaims(request.getResetToken());
        if (jwtClaimsSet == null || !jwtClaimsSet.getSubject().equals(AccountConstant.TokenSubject.RESET_PASSWORD.toString())) {
            throw new BadCredentialsException("Token xác thực OTP không hợp lệ hoặc đã hết hạn.");
        }

        Long accountId;
        try {
            accountId = Long.parseLong(jwtClaimsSet.getStringClaim("accountId"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new BadCredentialsException("Tài khoản liên kết với token không tồn tại."));

        account.setPassword(passwordEncoder.encode(request.getNewPassword()));
        account.setUpdatedAt(new Date().getTime());
        accountRepository.save(account);

        jwtService.deleteToken(account.getId());
    }
}
