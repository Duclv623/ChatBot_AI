package com.jurisai.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileResponseDTO {
    private Long accountId;
    private Long userId;
    private String email;
    private String fullName;
    private String gender;
    private String dateOfBirth;
    private String role;
    private String createdAt;
    private String updatedAt;
}
