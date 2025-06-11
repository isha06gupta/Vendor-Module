package com.vendormodule.Vendor.Module.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    private final Map<String, String> otpStorage = new HashMap<>();

    // Generate a 6-digit OTP
    public String generateOtp(String email) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        otpStorage.put(email, otp);
        return otp;
    }

    // Verify the OTP
    public boolean verifyOtp(String email, String enteredOtp) {
        String validOtp = otpStorage.get(email);
        return validOtp != null && validOtp.equals(enteredOtp);
    }

    // Optional: Remove OTP after successful verification
    public void clearOtp(String email) {
        otpStorage.remove(email);
    }
}
