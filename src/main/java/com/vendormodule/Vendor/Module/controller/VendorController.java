package com.vendormodule.Vendor.Module.controller;

import com.vendormodule.Vendor.Module.model.Vendor;
import com.vendormodule.Vendor.Module.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/vendors")
@CrossOrigin(origins = "http://localhost:9090") // Adjust as per your frontend port
public class VendorController {

    private final VendorService vendorService;

    @Autowired
    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    // Vendor Registration
    @PostMapping("/register")
    public ResponseEntity<Vendor> registerVendor(@RequestBody Vendor vendor) {
        try {
            Vendor registeredVendor = vendorService.registerVendor(vendor);
            return new ResponseEntity<>(registeredVendor, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    // Get Vendor by ID
    @GetMapping("/{id}")
    public ResponseEntity<Vendor> getVendorById(@PathVariable Long id) {
        return vendorService.getVendorById(id)
                .map(vendor -> new ResponseEntity<>(vendor, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update Vendor
    @PutMapping("/{id}")
    public ResponseEntity<Vendor> updateVendor(@PathVariable Long id, @RequestBody Vendor vendor) {
        if (!id.equals(vendor.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Vendor updatedVendor = vendorService.updateVendor(vendor);
            return new ResponseEntity<>(updatedVendor, HttpStatus.OK);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    // Upload documents
    @PostMapping(value = "/upload-documents/{vendorId}", consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadAllVendorDocuments(
            @PathVariable Long vendorId,
            @RequestParam("incorporationCertUpload") MultipartFile incorporationCertUpload,
            @RequestParam("panUpload") MultipartFile panUpload,
            @RequestParam("gstUpload") MultipartFile gstUpload,
            @RequestParam("bankUpload") MultipartFile bankUpload,
            @RequestParam("certUpload") MultipartFile certUpload) {

        try {
            if (incorporationCertUpload.isEmpty() || panUpload.isEmpty() || gstUpload.isEmpty()
                    || bankUpload.isEmpty() || certUpload.isEmpty()) {
                return new ResponseEntity<>("All mandatory files must be provided.", HttpStatus.BAD_REQUEST);
            }

            Map<String, MultipartFile> documents = new HashMap<>();
            documents.put("incorporationCertUpload", incorporationCertUpload);
            documents.put("panUpload", panUpload);
            documents.put("gstUpload", gstUpload);
            documents.put("bankUpload", bankUpload);
            documents.put("certUpload", certUpload);

            vendorService.uploadVendorDocuments(vendorId, documents);
            return new ResponseEntity<>("Documents uploaded successfully for vendor " + vendorId, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Error processing documents: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity<>("Error storing documents: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
