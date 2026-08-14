package com.jurisai.service;

import com.jurisai.dto.request.*;
import com.jurisai.dto.response.ChangePasswordResponseDTO;
import com.jurisai.dto.response.ForgotPasswordResponseDTO;
import com.jurisai.dto.response.LoginResponseDTO;
import com.jurisai.dto.response.VerifyResetPasswordResponseDTO;
import com.jurisai.entity.Account;

public interface AccountService {
    Account register(RegisterRequestDTO request);
    LoginResponseDTO login(LoginRequestDTO request);
    void verifyOtp(VerifyAccountRequestDTO request);
    void logout(Long accountId);
    ChangePasswordResponseDTO changePassword(Long accountId, ChangePasswordRequestDTO request);
    ForgotPasswordResponseDTO forgotPassword(ForgotPasswordRequestDTO request);
    VerifyResetPasswordResponseDTO verifyOtpResetPassword(VerifyResetPasswordRequestDTO request);
    void resetPassword(ResetPasswordRequestDTO request);
}
