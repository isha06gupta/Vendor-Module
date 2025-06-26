package com.vendormodule.Vendor.Module.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

import com.vendormodule.Vendor.Module.service.ClientRegistrationService;
import com.vendormodule.Vendor.Module.dto.ClientRegistrationDTO;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/client")
@CrossOrigin(origins = "*")
public class ClientController {

    @Autowired
    private ClientRegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerClient(@RequestBody ClientRegistrationDTO dto) {
        System.out.println("=== CLIENT REGISTRATION DEBUG ===");
        System.out.println("Received DTO: " + dto);
        System.out.println("Client Info: " + dto.getClientInfo());
        System.out.println("Company Info: " + dto.getCompanyInfo());
        
        if (dto.getClientInfo() != null) {
            System.out.println("Client Name: " + dto.getClientInfo().getFullName());
            System.out.println("Client Email: " + dto.getClientInfo().getOfficialEmail());
        }
        
        if (dto.getCompanyInfo() != null) {
            System.out.println("Company Name: " + dto.getCompanyInfo().getCompanyName());
            System.out.println("Company Email: " + dto.getCompanyInfo().getOfficialEmail());
        }
        
        try {
            // Call service to register client and get company ID
            Integer companyId = registrationService.registerClient(dto);
            System.out.println("Service returned company ID: " + companyId);
            
            // Return response with company ID
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Client registered successfully");
            response.put("companyId", companyId);
            
            System.out.println("Returning response: " + response);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.out.println("ERROR in registration: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Registration failed: " + e.getMessage());
        }
    }
}
