package com.jurisai.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequestDTO {
    @JsonProperty("newPassword")
    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 8, max = 100, message = "Mật khẩu phải có từ 8 đến 100 kí tự")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$&*]).*$",
            message = "Mật khẩu mới phải chứa ít nhất một ký tự viết hoa và một ký tự đặc biệt (!@#$&*)")
    private String newPassword;

    @NotBlank(message = "Token không được để trống")
    private String resetToken;
}
