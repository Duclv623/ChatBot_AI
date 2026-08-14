package com.jurisai.service.impl;

import com.jurisai.dao.repository.AccountRepository;
import com.jurisai.dao.repository.UserRepository;
import com.jurisai.dto.request.UserUpdateRequestDTO;
import com.jurisai.dto.response.UserProfileResponseDTO;
import com.jurisai.entity.Account;
import com.jurisai.entity.User;
import com.jurisai.entity.pk.UserPK;
import com.jurisai.mapper.UserMapper;
import com.jurisai.service.JWTService;
import com.jurisai.service.UserService;
import com.jurisai.util.SnowflakeIdGenUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final SnowflakeIdGenUtils snowflakeIdGenUtils;
    private final AccountRepository accountRepository;
    private final JWTService jwtService;
    private final UserMapper userMapper;

    // create new user and new account concurrently
    @Override
    public User createUser(Long accountId) {
        Long userId = snowflakeIdGenUtils.nextId();

        UserPK userPK = UserPK.builder()
                .userId(userId)
                .accountId(accountId)
                .build();

        User user = userMapper.toEntity();
        user.setId(userPK);

        return userRepository.save(user);
    }

    @Override
    public User getUserByAccountId(Long accountId) {
        return userRepository.findByIdAccountId(accountId)
                .orElseThrow();
    }


    // add user profile to cache
    @Override
    @Cacheable(value = "userProfile", key = "#userId")
    public UserProfileResponseDTO getUserProfile(Long userId) {
        User user = userRepository.findByIdUserId(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với ID: " + userId));
        Account account = accountRepository.findById(user.getId().getAccountId())
                .orElseThrow(() -> new RuntimeException("Dữ liệu không nhất quán: Không tìm thấy tài khoản cho người dùng ID: " + userId));

        UserProfileResponseDTO response = userMapper.toResponse(user);
        response.setEmail(account.getEmail());

        return response;
    }

    // delete user profile from cache after user update
    @Override
    @CacheEvict(value = "userProfile", key = "#userId")
    public User updateUser(Long userId, UserUpdateRequestDTO request) {
        User user = userRepository.findByIdUserId(userId)
                .orElseThrow();

        userMapper.toEntity(request, user);

        return userRepository.save(user);
    }

//    List<UserProfileResponseDTO> getAllUsers(){
//        List<User> users = userRepository.findAll();
//        List<UserProfileResponseDTO> userProfileResponseDTOs = new ArrayList<>();
//
//    }

    // delete user profile from cache
    @Override
    @Transactional
    @CacheEvict(value = "userProfile", key = "#userId")
    public void deleteUserAndAccount(Long userId) {
        User user = userRepository.findByIdUserId(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với ID: " + userId));

        Long accountId = user.getId().getAccountId();

        userRepository.delete(user);
        accountRepository.deleteById(accountId);

        jwtService.deleteToken(accountId);
    }

    // this function is call when logout
    @Override
    @CacheEvict(value = "userProfile", key = "#userId")
    public void evictUserProfileCache(Long userId) {
    }
}
