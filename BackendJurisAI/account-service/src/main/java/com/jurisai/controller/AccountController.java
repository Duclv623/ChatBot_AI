package com.jurisai.controller;

import com.jurisai.dto.request.*;
import com.jurisai.dto.response.*;
import com.jurisai.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/register")
    public CustomResponse<Void> register(@Valid @RequestBody RegisterRequestDTO request) {
        accountService.register(request);
        return new CustomResponse<>(true, "Đăng kí thành công, vui lòng xác thực email");
    }

    @PostMapping("/login")
    public CustomResponse<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = accountService.login(request);
        return new CustomResponse<>(true, response,"Đăng nhập thành công");
    }

    @PostMapping("/verify")
    public CustomResponse<Void> verify(@Valid @RequestBody VerifyAccountRequestDTO request) {
        accountService.verifyOtp(request);
        return new CustomResponse<>(true, "Xác thực tài khoản thành công");
    }

    @PostMapping("/logout")
    public CustomResponse<Void> logout(@RequestHeader(value = "X-Account-ID") String accountIdStr) {
        Long accountId = Long.parseLong(accountIdStr);
        if (accountId == null) {
            return new CustomResponse<>(false, "Lỗi: Thiếu header X-Account-ID.");
        }
        accountService.logout(accountId);
        return new CustomResponse<>(true, "Đăng xuất thành công!");
    }

    @PostMapping("/change-password")
    public CustomResponse<ChangePasswordResponseDTO> changePassword(@RequestHeader("X-Account-ID") String accountIdStr,
                                                                    @Valid @RequestBody ChangePasswordRequestDTO request) {
        Long accountId = Long.parseLong(accountIdStr);
        ChangePasswordResponseDTO response = accountService.changePassword(accountId, request);
        return new CustomResponse<>(true, response, "Đổi mật khẩu thành công. Vui lòng đăng nhập lại.");
    }

    @PostMapping("/forgot-password")
    public CustomResponse<ForgotPasswordResponseDTO> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDTO request) {
        ForgotPasswordResponseDTO response = accountService.forgotPassword(request);
        return new CustomResponse<>(true, response, "Yêu cầu thành công.");
    }

    @PostMapping("/reset-password/verify")
    public CustomResponse<VerifyResetPasswordResponseDTO> verifyResetPassword(@Valid @RequestBody VerifyResetPasswordRequestDTO request) {
        VerifyResetPasswordResponseDTO response = accountService.verifyOtpResetPassword(request);
        return new CustomResponse<>(true, response, "Xác thực tài  thành công.");
    }

    @PostMapping("/reset-password")
    public CustomResponse<Void> resetPassword(@Valid @RequestBody ResetPasswordRequestDTO request) {
        accountService.resetPassword(request);
        return new CustomResponse<>(true, "Mật khẩu của bạn đã được đặt lại thành công.");
    }
}
