package com.jurisai.service;

import com.nimbusds.jwt.JWTClaimsSet;
import reactor.core.publisher.Mono;

public interface TokenValidationService {
    public Mono<JWTClaimsSet> validateAndGetClaims(String token);
}
