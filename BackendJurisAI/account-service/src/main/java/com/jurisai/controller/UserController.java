package com.jurisai.controller;

import com.jurisai.dto.request.UserUpdateRequestDTO;
import com.jurisai.dto.response.CustomResponse;
import com.jurisai.dto.response.UserProfileResponseDTO;
import com.jurisai.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // role: USER
    @GetMapping("/me")
    public CustomResponse<UserProfileResponseDTO> getMyProfile(@RequestHeader("X-User-ID") String userIdStr){
        Long userId = Long.parseLong(userIdStr);
        UserProfileResponseDTO response = userService.getUserProfile(userId);
        return new CustomResponse<>(true, response, "Lấy thông tin người dùng thành công.");
    }

    @PutMapping("/me")
    public CustomResponse<Void> updateUser(@RequestHeader("X-User-ID") String userIdStr,
                                           @Valid @RequestBody UserUpdateRequestDTO request){
        Long userId = Long.parseLong(userIdStr);
        userService.updateUser(userId, request);
        return new CustomResponse<>(true, "Thay đổi thông tin người dùng thành công");
    }

    // role: ADMIN
//    @GetMapping("/{userId}")
//    public CustomResponse<UserProfileResponseDTO> getUserById(@PathVariable Long userId){
//        UserProfileResponseDTO response = userService.getUserProfile(userId);
//        return new CustomResponse<>(true, response, "Lấy thông tin người dùng thành công.");
//    }

//    @GetMapping("/all")
//    public CustomResponse<List<UserProfileResponseDTO>> getAllUsers(){
//
//    }

    @DeleteMapping("/{userId}")
    public CustomResponse<Void> deleteUser(@PathVariable Long userId){
        userService.deleteUserAndAccount(userId);
        return new CustomResponse<>(true, "Xóa thông tin người dùng thành công");
    }
}
