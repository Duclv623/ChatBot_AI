package com.jurisai.service;

import com.jurisai.util.AccountConstant;
import com.nimbusds.jwt.JWTClaimsSet;

public interface JWTService {
    String generateToken(Long accountId, Long userId, String email, AccountConstant.TokenSubject subject);
    void deleteToken(Long accountId);
    JWTClaimsSet validateAndGetClaims(String token);
}
