package com.jurisai.service.impl;

import com.jurisai.service.TokenValidationService;
import com.jurisai.util.JwtUtil;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenValidationServiceImpl implements TokenValidationService {
    private final JwtUtil jwtUtil;
    private final ReactiveRedisTemplate<String, String> redisTemplate;
    private static final String JWT_PREFIX_REDIS = "jwt:";

    @Override
    @Cacheable(value = "validatedTokens", key = "#token")
    public Mono<JWTClaimsSet> validateAndGetClaims(String token) {
        JWTClaimsSet jwtClaimsSet = jwtUtil.getClaims(token);
        if (jwtClaimsSet == null) {
            return Mono.empty();
        }

        try{
            String accountId = jwtClaimsSet.getStringClaim("accountId");
            String redisKey = JWT_PREFIX_REDIS + accountId;

            return redisTemplate.opsForValue().get(redisKey)
                    .filter(storedToken -> token.equals(storedToken))
                    .map(storedToken -> jwtClaimsSet);
        } catch (Exception e) {
            return Mono.empty();
        }
    }
}
