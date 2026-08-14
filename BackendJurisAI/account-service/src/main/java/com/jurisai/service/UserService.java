package com.jurisai.service;

import com.jurisai.dto.request.UserUpdateRequestDTO;
import com.jurisai.dto.response.UserProfileResponseDTO;
import com.jurisai.entity.User;

public interface UserService {
    User createUser(Long accountId);
    User getUserByAccountId(Long accountId);
    UserProfileResponseDTO getUserProfile(Long userId);
    User updateUser(Long userId, UserUpdateRequestDTO request);
    void deleteUserAndAccount(Long userId);
    void evictUserProfileCache(Long userId);
}
