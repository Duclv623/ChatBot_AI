package com.jurisai.util;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${app.jwt.secret}")
    private String secretKey;

    public JWTClaimsSet getClaims(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(this.secretKey.getBytes());

            if (signedJWT.verify(verifier) && !isTokenExpired(signedJWT)) {
                return signedJWT.getJWTClaimsSet();
            }
        } catch (Exception e) {
        }
        return null;
    }

    private boolean isTokenExpired(SignedJWT signedJWT) throws Exception {
        return signedJWT.getJWTClaimsSet().getExpirationTime().before(new Date());
    }
}