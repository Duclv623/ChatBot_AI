package com.jurisai.service.impl;

import com.jurisai.service.JWTService;
import com.jurisai.util.AccountConstant;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Duration;
import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService {
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${jwt.secret}")
    private String secretKey;

    private static final String JWT_PREFIX = "jwt:";

    @Override
    public String generateToken(Long accountId, Long userId, String email, AccountConstant.TokenSubject subject) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // get duration from constant TokenSubject
        Duration expirationTime = subject.getDuration();
        long expirationTimeMilliseconds = expirationTime.toMillis();
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTimeMilliseconds);

        Date now = new Date();

        // get scopes from constant TokenSubject
        List<String> scopes = subject.getScopes();

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .issuer("jurisai")
                .subject(subject.getSubject())
                .expirationTime(expirationDate)
                .notBeforeTime(now)
                .issueTime(now)
                .claim("scope", scopes)
                .claim("accountId", String.valueOf(accountId))
                .claim("userId", String.valueOf(userId))
                .claim("email", email)
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(secretKey.getBytes()));
            String token = jwsObject.serialize();

            // only save in redis if access token
            if (subject == AccountConstant.TokenSubject.USER_ACCESS
                    || subject == AccountConstant.TokenSubject.ADMIN_ACCESS) {
                String redisKey = JWT_PREFIX + accountId;
                // save token into redis
                redisTemplate.opsForValue().set(redisKey, token, expirationTime);
            }

            return token;
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteToken(Long accountId) {
        String redisKey = JWT_PREFIX + accountId;
        redisTemplate.delete(redisKey);
    }

    @Override
    public JWTClaimsSet validateAndGetClaims(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(this.secretKey.getBytes());

            boolean isSignatureValid = signedJWT.verify(verifier);
            boolean isExpired = signedJWT.getJWTClaimsSet().getExpirationTime().before(new Date());

            if(isSignatureValid || !isExpired){
                return signedJWT.getJWTClaimsSet();
            }
        } catch (ParseException | JOSEException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
