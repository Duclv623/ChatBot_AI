package com.jurisai.service.impl;

import com.jurisai.service.VerifyAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class VerifyAccountServiceImpl implements VerifyAccountService {
    private final RedisTemplate<String, String> redisTemplate;
    private static final String OTP_PREFIX = "otp:";
    private static final long OTP_EXPIRATION_MINUTES = 2;

    @Override
    public String generateAndSaveOtp(String email) {
        String otp = "1111";

        String redisKey = OTP_PREFIX + email;
        // save otp in redis with short living time
        redisTemplate.opsForValue().set(redisKey, otp, OTP_EXPIRATION_MINUTES, TimeUnit.MINUTES);

        return otp;
    }

    @Override
    public boolean verifyOtp(String email, String otp) {
        String redisKey = OTP_PREFIX + email;
        String verifiedOtp = redisTemplate.opsForValue().get(redisKey);

        if (verifiedOtp != null && verifiedOtp.equals(otp)) {
            redisTemplate.delete(redisKey);
            return true;
        }
        return false;
    }
}
