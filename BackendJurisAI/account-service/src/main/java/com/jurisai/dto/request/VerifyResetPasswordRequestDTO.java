package com.jurisai.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyResetPasswordRequestDTO {
    @NotBlank(message = "Mã OTP không được để trống")
    private String otp;

    @NotBlank(message = "Token không được để trống")
    private String otpResetToken;
}
