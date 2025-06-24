package com.vendormodule.Vendor.Module.service;

import com.vendormodule.Vendor.Module.model.Vendor;
import com.vendormodule.Vendor.Module.repository.VendorRepository;
import com.vendormodule.Vendor.Module.filestorage.FileStorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
public class VendorService {

    private final VendorRepository vendorRepository;
    private final FileStorageService fileStorageService;

    @Autowired
    public VendorService(VendorRepository vendorRepository, FileStorageService fileStorageService) {
        this.vendorRepository = vendorRepository;
        this.fileStorageService = fileStorageService;
    }

    @Transactional
    public Vendor registerVendor(Vendor vendor) {
        if (vendorRepository.findByEmail(vendor.getEmail()).isPresent()) {
            throw new RuntimeException("Vendor with this email already exists.");
        }
        if (vendorRepository.findByGstin(vendor.getGstin()).isPresent()) {
            throw new RuntimeException("Vendor with this GSTIN already exists.");
        }
        return vendorRepository.save(vendor);
    }

    @Transactional(readOnly = true)
    public Optional<Vendor> getVendorById(Long id) {
        return vendorRepository.findById(id);
    }

    @Transactional
    public Vendor updateVendor(Vendor vendor) {
        if (!vendorRepository.existsById(vendor.getId())) {
            throw new RuntimeException("Vendor not found with ID: " + vendor.getId());
        }

        Optional<Vendor> existingEmailVendor = vendorRepository.findByEmail(vendor.getEmail());
        if (existingEmailVendor.isPresent() && !existingEmailVendor.get().getId().equals(vendor.getId())) {
            throw new RuntimeException("Another vendor with this email already exists.");
        }

        Optional<Vendor> existingGstinVendor = vendorRepository.findByGstin(vendor.getGstin());
        if (existingGstinVendor.isPresent() && !existingGstinVendor.get().getId().equals(vendor.getId())) {
            throw new RuntimeException("Another vendor with this GSTIN already exists.");
        }

        return vendorRepository.save(vendor);
    }

    @Transactional
    public Vendor uploadVendorDocuments(Long vendorId, Map<String, MultipartFile> documents) throws IOException {
        Optional<Vendor> optionalVendor = vendorRepository.findById(vendorId);
        if (optionalVendor.isEmpty()) {
            throw new RuntimeException("Vendor not found with ID: " + vendorId);
        }

        Vendor vendor = optionalVendor.get();
        String vendorSubfolder = vendorId.toString();

        for (Map.Entry<String, MultipartFile> entry : documents.entrySet()) {
            String fieldName = entry.getKey();
            MultipartFile file = entry.getValue();

            if (file.isEmpty()) continue;

            String storedPath = fileStorageService.storeFile(file, vendorSubfolder);

            switch (fieldName) {
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
                    vendor.setCertIncorporationPartnershipUploaded(true);
                    vendor.setCertIncorporationPartnershipPath(storedPath);
                    break;
                default:
                    System.err.println("Unrecognized document field: " + fieldName);
                    break;
            }
        }

        vendor.setStatus("DOCUMENTS_UPLOADED");
        return vendorRepository.save(vendor);
    }
}
