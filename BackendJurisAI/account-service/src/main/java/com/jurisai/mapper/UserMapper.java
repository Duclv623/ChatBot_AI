package com.jurisai.mapper;

import com.jurisai.dto.request.UserUpdateRequestDTO;
import com.jurisai.dto.response.UserProfileResponseDTO;
import com.jurisai.entity.User;
import com.jurisai.util.AccountConstant;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class UserMapper {
    public static User toEntity(){
        Long timestamp = new Date().getTime();

        return new User().builder()
                .role(AccountConstant.Role.USER.getValue())
                .createdAt(timestamp)
                .updatedAt(timestamp)
                .build();
    }

    public static void toEntity(UserUpdateRequestDTO request, User user){
        Long timestamp = new Date().getTime();

        if(request.getGender() != null){
            if(request.getGender().equalsIgnoreCase(AccountConstant.Gender.MALE.toString())){
                user.setGender(AccountConstant.Gender.MALE.getValue());
            } else if(request.getGender().equalsIgnoreCase(AccountConstant.Gender.FEMALE.toString())){
                user.setGender(AccountConstant.Gender.FEMALE.getValue());
            } else if(request.getGender().equalsIgnoreCase(AccountConstant.Gender.OTHER.toString())){
                user.setGender(AccountConstant.Gender.OTHER.getValue());
            }
        }

        user.setFullName(request.getFullName());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setUpdatedAt(timestamp);
    }

    public static UserProfileResponseDTO toResponse(User user){
        String formatCreatedAt = formatTimestamp(user.getCreatedAt());
        String formatUpdatedAt = formatTimestamp(user.getUpdatedAt());

        String gender = null;
        if(user.getGender() != null){
            if(user.getGender().equals(AccountConstant.Gender.MALE.getValue())){
                gender = AccountConstant.Gender.MALE.toString();
            } else if(user.getGender().equals(AccountConstant.Gender.FEMALE.getValue())){
                gender = AccountConstant.Gender.FEMALE.toString();
            } else if(user.getGender().equals(AccountConstant.Gender.OTHER.getValue())){
                gender = AccountConstant.Gender.OTHER.toString();
            }
        }

        String role = null;
        if(user.getRole() != null){
            if(user.getRole().equals(AccountConstant.Role.USER.getValue())){
                role = AccountConstant.Role.USER.toString();
            } else if(user.getRole().equals(AccountConstant.Role.ADMIN.getValue())){
                role = AccountConstant.Role.ADMIN.toString();
            }
        }

        return new UserProfileResponseDTO().builder()
                .accountId(user.getId().getAccountId())
                .userId(user.getId().getUserId())
                .fullName(user.getFullName())
                .gender(gender)
                .dateOfBirth(user.getDateOfBirth())
                .role(role)
                .createdAt(formatCreatedAt)
                .updatedAt(formatUpdatedAt)
                .build();
    }

    private static String formatTimestamp(Long epochMilli) {
        if (epochMilli == null) {
            return null;
        }

        Instant instant = Instant.ofEpochMilli(epochMilli);

        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
                .withZone(ZoneId.of("Asia/Ho_Chi_Minh"));

        return formatter.format(instant);
    }

}
