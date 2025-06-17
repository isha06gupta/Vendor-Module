package com.ceil.vendor.vendorportal.controller;

import com.ceil.vendor.vendorportal.dto.LoginRequest;
import com.ceil.vendor.vendorportal.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employee") // Base path for employee related APIs
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public ResponseEntity<String> loginEmployee(@RequestBody LoginRequest loginRequest) {
        boolean isAuthenticated = employeeService.authenticateEmployee(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        if (isAuthenticated) {
            // In a real application, you'd generate a JWT token here and send it back.
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed. Invalid official email or password.");
        }
    }
}
