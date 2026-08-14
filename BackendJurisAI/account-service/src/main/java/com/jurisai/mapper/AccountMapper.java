package com.jurisai.mapper;

import com.jurisai.dto.request.RegisterRequestDTO;
import com.jurisai.entity.Account;
import com.jurisai.util.AccountConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class AccountMapper {
    public static Account toEntity(RegisterRequestDTO request) {
        Long timestamp = new Date().getTime();
        return Account.builder()
                .email(request.getEmail())
                .isActived(AccountConstant.IsVerified.UNVERIFIED.getValue())
                .provider(AccountConstant.AuthProvier.LOCAL.getValue())
                .createdAt(timestamp)
                .updatedAt(timestamp)
                .build();
    }
}
