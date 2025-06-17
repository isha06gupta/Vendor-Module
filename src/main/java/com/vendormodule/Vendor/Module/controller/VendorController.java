package com.ceil.vendor.vendorportal.controller;

import com.ceil.vendor.vendorportal.model.vendor;
import com.ceil.vendor.vendorportal.service.VendorService;
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
@CrossOrigin(origins = "http://localhost:8080") 
public class VendorController {

    private final VendorService vendorService;

    @Autowired
    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @PostMapping("/register")
    public ResponseEntity<vendor> registerVendor(@RequestBody vendor vendor) {
        try {
            vendor registeredVendor = vendorService.registerVendor(vendor);
            return new ResponseEntity<>(registeredVendor, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<vendor> getVendorById(@PathVariable Long id) {
        return vendorService.getVendorById(id)
                .map(vendor -> new ResponseEntity<>(vendor, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<vendor> updateVendor(@PathVariable Long id, @RequestBody vendor vendor) {
        if (!id.equals(vendor.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            vendor updatedVendor = vendorService.updateVendor(vendor);
            return new ResponseEntity<>(updatedVendor, HttpStatus.OK);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    /**
     * Endpoint to upload all required documents for a vendor.
     * The files are sent as MultipartFile objects within the request.
     *
     * @param vendorId The ID of the vendor to upload documents for.
     * @param incorporationCertUpload Business and Legal Proof file.
     * @param panUpload Company PAN Card file.
     * @param gstUpload GST Registration Certification file.
     * @param bankUpload Bank Passbook Copy file.
     * @param certUpload Certification Of Incorporation/Partnership Deed file.
     * @return ResponseEntity indicating success or failure.
     */
    @PostMapping(value = "/upload-documents/{vendorId}", consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadAllVendorDocuments(
            @PathVariable Long vendorId,
            @RequestParam("incorporationCertUpload") MultipartFile incorporationCertUpload,
            @RequestParam("panUpload") MultipartFile panUpload,
            @RequestParam("gstUpload") MultipartFile gstUpload,
            @RequestParam("bankUpload") MultipartFile bankUpload,
            @RequestParam("certUpload") MultipartFile certUpload) { // Matches name="certUpload" in HTML
        try {
            if (incorporationCertUpload.isEmpty() || panUpload.isEmpty() || gstUpload.isEmpty() ||
                bankUpload.isEmpty() || certUpload.isEmpty()) {
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