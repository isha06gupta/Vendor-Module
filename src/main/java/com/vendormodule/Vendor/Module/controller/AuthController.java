package com.vendormodule.Vendor.Module.controller;

import com.vendormodule.Vendor.Module.dto.CaptchaResponse;
import com.vendormodule.Vendor.Module.entity.User;
import com.vendormodule.Vendor.Module.repository.UserRepository;
import com.vendormodule.Vendor.Module.service.EmailService;
import com.vendormodule.Vendor.Module.service.OtpService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final String secret = "6Ld7MVUrAAAAAIXtjzS5LJY7ko5nUCYCJC8omnAP";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OtpService otpService;

    private final Map<String, User> pendingUsers = new HashMap<>();

    // Test endpoint to verify the controller is working
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(Map.of("message", "Auth controller is working", "timestamp", System.currentTimeMillis()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam("g-recaptcha-response") String captcha,
                                      @RequestParam("name") String name,
                                      @RequestParam("email") String email,
                                      @RequestParam("password") String password) {

        System.out.println("=== REGISTER REQUEST RECEIVED ===");
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Captcha: " + (captcha != null ? "Present" : "Missing"));

        try {
            if (!verifyCaptcha(captcha)) {
                return ResponseEntity.badRequest().body(Map.of("error", "reCAPTCHA verification failed"));
            }

            if (userRepository.findByEmail(email).isPresent()) {
                return ResponseEntity.badRequest().body(Map.of("error", "User with this email already exists"));
            }

            String hashedPassword = passwordEncoder.encode(password);
            User pendingUser = new User(name, email, hashedPassword);
            pendingUsers.put(email, pendingUser);
            String otp = otpService.generateOtp(email);
            emailService.sendOtpEmail(email, otp);

            return ResponseEntity.ok(Map.of("message", "OTP sent to your email"));
        } catch (Exception e) {
            System.err.println("Registration error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Registration failed: " + e.getMessage()));
        }
    }

    private boolean verifyCaptcha(String captcha) {
        String verifyUrl = "https://www.google.com/recaptcha/api/siteverify";
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<CaptchaResponse> response = restTemplate.postForEntity(
                    verifyUrl + "?secret={secret}&response={response}",
                    null,
                    CaptchaResponse.class,
                    secret, captcha
            );

            CaptchaResponse captchaResponse = response.getBody();
            return captchaResponse != null && captchaResponse.isSuccess();

        } catch (RestClientException e) {
            System.err.println("Error during CAPTCHA verification: " + e.getMessage());
            return false;
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam("email") String email,
                                       @RequestParam("otp") String otp) {

        System.out.println("=== OTP VERIFICATION REQUEST ===");
        System.out.println("Email: " + email);
        System.out.println("OTP: " + otp);

        try {
            boolean isValid = otpService.verifyOtp(email, otp);

            if (!isValid) {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid OTP"));
            }

            User userToSave = pendingUsers.get(email);
            if (userToSave == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "User registration session expired"));
            }

            userRepository.save(userToSave);
            otpService.clearOtp(email);
            pendingUsers.remove(email);

            return ResponseEntity.ok(Map.of("message", "OTP Verified"));
        } catch (Exception e) {
            System.err.println("OTP verification error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "OTP verification failed: " + e.getMessage()));
        }
    }

    @PostMapping("/api-login")  // Changed endpoint name to avoid conflict
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        System.out.println("=== LOGIN REQUEST RECEIVED ===");
        System.out.println("Request body: " + credentials);
        
        try {
            String email = credentials.get("email");
            String password = credentials.get("password");

            System.out.println("Email: " + email);
            System.out.println("Password: " + (password != null ? "[PRESENT]" : "[MISSING]"));

            if (email == null || password == null) {
                System.out.println("Missing email or password");
                return ResponseEntity.badRequest().body(Map.of("error", "Email and password are required"));
            }

            Optional<User> optionalUser = userRepository.findByEmail(email);
            System.out.println("User found: " + optionalUser.isPresent());

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                boolean passwordMatches = passwordEncoder.matches(password, user.getPassword());
                System.out.println("Password matches: " + passwordMatches);
                
                if (passwordMatches) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("id", user.getId());
                    response.put("email", user.getEmail());
                    response.put("name", user.getname()); // Fixed: was getname()
                    System.out.println("Login successful, returning: " + response);
                    return ResponseEntity.ok(response);
                }
            }

            System.out.println("Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials"));
                    
        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Login failed: " + e.getMessage()));
        }
    }
}
