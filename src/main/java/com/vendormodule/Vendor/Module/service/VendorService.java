package com.ceil.vendor.vendorportal.service;

import com.ceil.vendor.vendorportal.model.vendor;
import com.ceil.vendor.vendorportal.repository.vendorRepository;
import com.ceil.vendor.vendorportal.filestorage.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map; 
import java.util.Optional;

@Service
public class VendorService {

    private final vendorRepository vendorRepository;
    private final FileStorageService fileStorageService;

    @Autowired
    public VendorService(vendorRepository vendorRepository, FileStorageService fileStorageService) {
        this.vendorRepository = vendorRepository;
        this.fileStorageService = fileStorageService;
    }

    @Transactional
    public vendor registerVendor(vendor vendor) {
        if (vendorRepository.findByEmail(vendor.getEmail()).isPresent()) {
            throw new RuntimeException("Vendor with this email already exists.");
        }
        if (vendorRepository.findByGstin(vendor.getGstin()).isPresent()) {
            throw new RuntimeException("Vendor with this GSTIN already exists.");
        }
        return vendorRepository.save(vendor);
    }

    @Transactional(readOnly = true)
    public Optional<vendor> getVendorById(Long id) {
        return vendorRepository.findById(id);
    }

    @Transactional
    public vendor updateVendor(vendor vendor) {
        if (!vendorRepository.existsById(vendor.getId())) {
            throw new RuntimeException("Vendor not found with ID: " + vendor.getId());
        }
        Optional<vendor> existingEmailVendor = vendorRepository.findByEmail(vendor.getEmail());
        if (existingEmailVendor.isPresent() && !existingEmailVendor.get().getId().equals(vendor.getId())) {
            throw new RuntimeException("Another vendor with this email already exists.");
        }
        Optional<vendor> existingGstinVendor = vendorRepository.findByGstin(vendor.getGstin());
        if (existingGstinVendor.isPresent() && !existingGstinVendor.get().getId().equals(vendor.getId())) {
            throw new RuntimeException("Another vendor with this GSTIN already exists.");
        }
        return vendorRepository.save(vendor);
    }

    /**
     * Handles the upload of multiple documents for a specific vendor.
     * Updates vendor entity with upload status and optionally file paths.
     *
     * @param vendorId The ID of the vendor.
     * @param documents A map where keys are form field names (e.g., "panUpload") and values are MultipartFile.
     * @return The updated Vendor entity.
     * @throws IOException If there's an issue storing any of the files.
     * @throws RuntimeException If vendor is not found or other business logic errors.
     */
    @Transactional
    public vendor uploadVendorDocuments(Long vendorId, Map<String, MultipartFile> documents) throws IOException {
        Optional<vendor> optionalVendor = vendorRepository.findById(vendorId);
        if (optionalVendor.isEmpty()) {
            throw new RuntimeException("Vendor not found with ID: " + vendorId);
        }
        vendor vendor = optionalVendor.get();

        String vendorSubfolder = vendorId.toString(); 

        for (Map.Entry<String, MultipartFile> entry : documents.entrySet()) {
            String documentFieldName = entry.getKey(); 
            MultipartFile file = entry.getValue();

            if (file.isEmpty()) {
                continue;
            }

            String storedPath = fileStorageService.storeFile(file, vendorSubfolder);
            
        switch (documentFieldName) {
            case "incorporationCertUpload":
                vendor.setIncorporationCertUploaded(true);
                vendor.setIncorporationCertPath(storedPath);
                break;
            case "panUpload":
                vendor.setPanCardUploaded(true); 
                vendor.setPanCardPath(storedPath);
                break;
            case "gstUpload":
                vendor.setGstCertUploaded(true); 
                vendor.setGstCertPath(storedPath);
                break;
            case "bankUpload":
                vendor.setBankProofUploaded(true); 
                vendor.setBankProofPath(storedPath);
                break;
            case "certUpload":
                vendor.setCertIncorporationPartnershipUploaded(true); // Corrected syntax and parameter
                vendor.setCertIncorporationPartnershipPath(storedPath);
                break;
                default:
                    System.err.println("Warning: Unrecognized document field name: " + documentFieldName);
                    break;
            }
        }
        vendor.setStatus("DOCUMENTS_UPLOADED"); 

        return vendorRepository.save(vendor);
    }
}
