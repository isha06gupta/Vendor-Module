package com.vendormodule.Vendor.Module.controller;

import com.vendormodule.Vendor.Module.entity.ClientCompanyInfo;
import com.vendormodule.Vendor.Module.repository.ClientCompanyInfoRepository;
import com.vendormodule.Vendor.Module.filestorage.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/client/documents")
@CrossOrigin(origins = "*")
public class ClientDocumentController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ClientCompanyInfoRepository companyInfoRepository;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadDocuments(
        @RequestParam("companyId") String companyIdStr,
        @RequestParam("panCard") MultipartFile panCard,
        @RequestParam("registrationProof") MultipartFile registrationProof,
        @RequestParam(value = "workOrder", required = false) MultipartFile workOrder
    ) {
        System.out.println("=== Document Upload Controller Debug ===");
        System.out.println("Raw companyId parameter: '" + companyIdStr + "'");
        System.out.println("PAN Card file: " + (panCard != null ? panCard.getOriginalFilename() + " (" + panCard.getSize() + " bytes)" : "null"));
        System.out.println("Registration Proof file: " + (registrationProof != null ? registrationProof.getOriginalFilename() + " (" + registrationProof.getSize() + " bytes)" : "null"));
        System.out.println("Work Order file: " + (workOrder != null ? workOrder.getOriginalFilename() + " (" + workOrder.getSize() + " bytes)" : "null"));
        
        try {
            // Parse companyId
            int companyId;
            try {
                companyId = Integer.parseInt(companyIdStr.trim());
                System.out.println("Parsed companyId: " + companyId);
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Failed to parse companyId: " + e.getMessage());
                return ResponseEntity.badRequest().body("Invalid company ID format: " + companyIdStr);
            }

            // Validate companyId
            if (companyId <= 0) {
                System.out.println("ERROR: Invalid company ID: " + companyId);
                return ResponseEntity.badRequest().body("Invalid company ID");
            }

            // Check if repository is available
            if (companyInfoRepository == null) {
                System.out.println("ERROR: companyInfoRepository is null");
                return ResponseEntity.status(500).body("Repository not available");
            }

            // Find company info
            System.out.println("Looking for company with ID: " + companyId);
            ClientCompanyInfo companyInfo;
            try {
                companyInfo = companyInfoRepository.findById(companyId).orElse(null);
            } catch (Exception e) {
                System.out.println("ERROR: Database query failed: " + e.getMessage());
                e.printStackTrace();
                return ResponseEntity.status(500).body("Database error: " + e.getMessage());
            }

            if (companyInfo == null) {
                System.out.println("ERROR: Company not found with ID: " + companyId);
                return ResponseEntity.badRequest().body("Company info not found with ID: " + companyId);
            }
            
            System.out.println("Found company: " + companyInfo.getCompanyName());

            // Validate required files
            if (panCard == null || panCard.isEmpty()) {
                System.out.println("ERROR: PAN Card file is missing");
                return ResponseEntity.badRequest().body("PAN Card file is required");
            }
            if (registrationProof == null || registrationProof.isEmpty()) {
                System.out.println("ERROR: Registration proof file is missing");
                return ResponseEntity.badRequest().body("Registration proof file is required");
            }

            // Check if FileStorageService is available
            if (fileStorageService == null) {
                System.out.println("ERROR: fileStorageService is null");
                return ResponseEntity.status(500).body("File storage service not available");
            }

            // Store files
            System.out.println("Storing PAN Card file...");
            String panCardPath;
            try {
                panCardPath = fileStorageService.storeFile(panCard, "pan_card");
                System.out.println("PAN Card stored at: " + panCardPath);
            } catch (Exception e) {
                System.out.println("ERROR: Failed to store PAN Card: " + e.getMessage());
                e.printStackTrace();
                return ResponseEntity.status(500).body("Failed to store PAN Card: " + e.getMessage());
            }
            
            System.out.println("Storing Registration Proof file...");
            String registrationProofPath;
            try {
                registrationProofPath = fileStorageService.storeFile(registrationProof, "registration_proof");
                System.out.println("Registration Proof stored at: " + registrationProofPath);
            } catch (Exception e) {
                System.out.println("ERROR: Failed to store Registration Proof: " + e.getMessage());
                e.printStackTrace();
                return ResponseEntity.status(500).body("Failed to store Registration Proof: " + e.getMessage());
            }
            
            String workOrderPath = null;
            if (workOrder != null && !workOrder.isEmpty()) {
                System.out.println("Storing Work Order file...");
                try {
                    workOrderPath = fileStorageService.storeFile(workOrder, "work_order");
                    System.out.println("Work Order stored at: " + workOrderPath);
                } catch (Exception e) {
                    System.out.println("ERROR: Failed to store Work Order: " + e.getMessage());
                    e.printStackTrace();
                    return ResponseEntity.status(500).body("Failed to store Work Order: " + e.getMessage());
                }
            }

            // Update company info
            System.out.println("Updating company info with file paths...");
            try {
                companyInfo.setCompanyPanCardPath(panCardPath);
                companyInfo.setRegistrationProofPath(registrationProofPath);
                companyInfo.setWorkOrderPath(workOrderPath);

                companyInfoRepository.save(companyInfo);
                System.out.println("Company info updated successfully");
            } catch (Exception e) {
                System.out.println("ERROR: Failed to update company info: " + e.getMessage());
                e.printStackTrace();
                return ResponseEntity.status(500).body("Failed to update company info: " + e.getMessage());
            }

            // Return success response
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Files uploaded and paths saved successfully");
            response.put("companyId", companyId);
            response.put("filesUploaded", workOrder != null ? 3 : 2);

            System.out.println("=== Upload completed successfully ===");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.out.println("=== UNEXPECTED ERROR in document upload ===");
            System.out.println("Error type: " + e.getClass().getSimpleName());
            System.out.println("Error message: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
        }
    }
}
