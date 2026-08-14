package com.jurisai.dto.request;

import com.jurisai.validation.annotations.ValidDate;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequestDTO {
    @Size(max = 100, message = "Họ tên không được vượt quá 100 ký tự")
    private String fullName;

    private String gender;

    @ValidDate
    private String dateOfBirth;
}
