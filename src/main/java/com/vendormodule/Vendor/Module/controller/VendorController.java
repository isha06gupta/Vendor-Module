package com.vendormodule.Vendor.Module.controller;

import com.vendormodule.Vendor.Module.entity.VendorPersonalInfo;
import com.vendormodule.Vendor.Module.entity.VendorDocuments;
import com.vendormodule.Vendor.Module.repository.VendorPersonalInfoRepository;
import com.vendormodule.Vendor.Module.repository.VendorDocumentsRepository;
import com.vendormodule.Vendor.Module.filestorage.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/vendor")
@CrossOrigin(origins = "*")
public class VendorController {

    @Autowired
    private VendorPersonalInfoRepository vendorPersonalInfoRepository;

    @Autowired
    private VendorDocumentsRepository vendorDocumentsRepository;

    @Autowired
    private FileStorageService fileStorageService;

    // Personal Info Registration
    @PostMapping("/personal-info")
    public ResponseEntity<?> savePersonalInfo(@RequestBody Map<String, Object> payload) {
        System.out.println("=== VENDOR PERSONAL INFO REGISTRATION ===");
        System.out.println("Received payload: " + payload);
        
        try {
            // Create VendorPersonalInfo entity
            VendorPersonalInfo vendor = new VendorPersonalInfo();
            vendor.setName((String) payload.get("name"));
            
            // Parse date of birth
            String dobString = (String) payload.get("dob");
            if (dobString != null && !dobString.isEmpty()) {
                vendor.setDateOfBirth(LocalDate.parse(dobString));
            }
            
            vendor.setContactPerson((String) payload.get("contact_person"));
            vendor.setPhone((String) payload.get("phone"));
            vendor.setEmail((String) payload.get("email"));
            vendor.setAddress((String) payload.get("address"));
            vendor.setGstin((String) payload.get("gstin"));
            vendor.setIsMsme((String) payload.get("is_msme"));
            
            System.out.println("Created vendor: " + vendor.getName());

            // Save vendor
            VendorPersonalInfo savedVendor = vendorPersonalInfoRepository.save(vendor);
            System.out.println("Saved vendor with ID: " + savedVendor.getId());

            // Return response with vendor ID
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Vendor registered successfully");
            response.put("id", savedVendor.getId());
            response.put("name", savedVendor.getName());

            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.out.println("ERROR in vendor registration: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Registration failed: " + e.getMessage());
        }
    }

    // Document Upload
    @PostMapping("/upload-documents/{vendorId}")
    public ResponseEntity<?> uploadDocuments(
        @PathVariable Integer vendorId,
        @RequestParam("incorporationCertUpload") MultipartFile incorporationCert,
        @RequestParam("panUpload") MultipartFile panCard,
        @RequestParam("gstUpload") MultipartFile gstCert,
        @RequestParam("bankUpload") MultipartFile bankPassbook,
        @RequestParam("certUpload") MultipartFile certification
    ) {
        System.out.println("=== VENDOR DOCUMENT UPLOAD ===");
        System.out.println("Vendor ID: " + vendorId);
        
        try {
            // Find vendor
            Optional<VendorPersonalInfo> vendorOpt = vendorPersonalInfoRepository.findById(vendorId);
            if (!vendorOpt.isPresent()) {
                return ResponseEntity.badRequest().body("Vendor not found with ID: " + vendorId);
            }
            
            VendorPersonalInfo vendor = vendorOpt.get();
            System.out.println("Found vendor: " + vendor.getName());

            // Validate required files
            if (incorporationCert.isEmpty() || panCard.isEmpty() || gstCert.isEmpty() || 
                bankPassbook.isEmpty() || certification.isEmpty()) {
                return ResponseEntity.badRequest().body("All documents are required");
            }

            // Store files
            String incorporationCertPath = fileStorageService.storeFile(incorporationCert, "vendor_docs/incorporation");
            String panCardPath = fileStorageService.storeFile(panCard, "vendor_docs/pan");
            String gstCertPath = fileStorageService.storeFile(gstCert, "vendor_docs/gst");
            String bankPassbookPath = fileStorageService.storeFile(bankPassbook, "vendor_docs/bank");
            String certificationPath = fileStorageService.storeFile(certification, "vendor_docs/certification");

            System.out.println("All files stored successfully");

            // Create or update vendor documents
            VendorDocuments vendorDocs = vendorDocumentsRepository.findByVendorPersonalInfoId(vendorId)
                .orElse(new VendorDocuments());
            
            vendorDocs.setIncorporationCertPath(incorporationCertPath);
            vendorDocs.setPanCardPath(panCardPath);
            vendorDocs.setGstCertPath(gstCertPath);
            vendorDocs.setBankPassbookPath(bankPassbookPath);
            vendorDocs.setCertificationPath(certificationPath);
            vendorDocs.setVendorPersonalInfo(vendor);

            vendorDocumentsRepository.save(vendorDocs);
            System.out.println("Vendor documents saved successfully");

            return ResponseEntity.ok("Documents uploaded successfully");
            
        } catch (Exception e) {
            System.out.println("ERROR in document upload: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Document upload failed: " + e.getMessage());
        }
    }

    // Get Personal Info
    @GetMapping("/personal-info/{vendorId}")
    public ResponseEntity<?> getPersonalInfo(@PathVariable Integer vendorId) {
        System.out.println("=== GET VENDOR PERSONAL INFO ===");
        System.out.println("Vendor ID: " + vendorId);
        
        try {
            Optional<VendorPersonalInfo> vendorOpt = vendorPersonalInfoRepository.findById(vendorId);
            if (!vendorOpt.isPresent()) {
                return ResponseEntity.badRequest().body("Vendor not found with ID: " + vendorId);
            }
            
            VendorPersonalInfo vendor = vendorOpt.get();
            System.out.println("Found vendor: " + vendor.getName());
            
            return ResponseEntity.ok(vendor);
            
        } catch (Exception e) {
            System.out.println("ERROR fetching vendor info: " + e.getMessage());
            return ResponseEntity.status(500).body("Failed to fetch vendor info: " + e.getMessage());
        }
    }

    // Get Documents
    @GetMapping("/documents/{vendorId}")
    public ResponseEntity<?> getDocuments(@PathVariable Integer vendorId) {
        System.out.println("=== GET VENDOR DOCUMENTS ===");
        System.out.println("Vendor ID: " + vendorId);
        
        try {
            Optional<VendorDocuments> docsOpt = vendorDocumentsRepository.findByVendorPersonalInfoId(vendorId);
            if (!docsOpt.isPresent()) {
                return ResponseEntity.badRequest().body("Documents not found for vendor ID: " + vendorId);
            }
            
            VendorDocuments docs = docsOpt.get();
            System.out.println("Found documents for vendor");
            
            return ResponseEntity.ok(docs);
            
        } catch (Exception e) {
            System.out.println("ERROR fetching vendor documents: " + e.getMessage());
            return ResponseEntity.status(500).body("Failed to fetch vendor documents: " + e.getMessage());
        }
    }

    // Get All Vendors
    @GetMapping("/all")
    public ResponseEntity<?> getAllVendors() {
        System.out.println("=== GET ALL VENDORS ===");
        
        try {
            return ResponseEntity.ok(vendorPersonalInfoRepository.findAll());
        } catch (Exception e) {
            System.out.println("ERROR fetching all vendors: " + e.getMessage());
            return ResponseEntity.status(500).body("Failed to fetch vendors: " + e.getMessage());
        }
    }

    // Test endpoint
    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        System.out.println("=== VENDOR CONTROLLER TEST ===");
        return ResponseEntity.ok("Vendor controller is working!");
    }
}
