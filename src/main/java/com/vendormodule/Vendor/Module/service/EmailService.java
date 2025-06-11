package com.vendormodule.Vendor.Module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String fromEmail;


    public void sendOtpEmail(String toEmail, String otp) {
    try {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("OTP Verification - CEIL");
        message.setText("Your OTP is: " + otp + "\nIt is valid for 5 minutes.");
        message.setFrom(fromEmail);

        mailSender.send(message);
        System.out.println("✅ OTP email sent to: " + toEmail); // ✅ Debug print
    } catch (Exception e) {
        System.out.println("❌ Failed to send email: " + e.getMessage());
    }
}
}


