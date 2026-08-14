package com.jurisai.service;

public interface VerifyAccountService {
    String generateAndSaveOtp(String email);
    boolean verifyOtp(String email, String otp);
}
