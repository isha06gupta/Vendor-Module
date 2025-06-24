package com.vendormodule.Vendor.Module.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.vendormodule.Vendor.Module.entity.VendorDocuments;
import com.vendormodule.Vendor.Module.repository.VendorDocumentsRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/vendor")
@CrossOrigin(origins = "*") // Adjust as needed
public class VendorDocumentController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final VendorDocumentsRepository documentsRepo;

    public VendorDocumentController(VendorDocumentsRepository documentsRepo) {
        this.documentsRepo = documentsRepo;
    }

    @PostMapping("/upload-documents")
    public ResponseEntity<?> uploadDocuments(
        @RequestParam("vendorId") Long vendorId,
        @RequestParam("businessLegalProof") MultipartFile businessLegalProof,
        @RequestParam("companyPanCard") MultipartFile companyPanCard,
        @RequestParam("gstRegistration") MultipartFile gstRegistration,
        @RequestParam("bankPassbook") MultipartFile bankPassbook,
        @RequestParam("certOfIncorp") MultipartFile certOfIncorp
    ) {
        try {
            String baseDir = uploadDir + "/vendor_" + vendorId + "/";
            Files.createDirectories(Paths.get(baseDir));

            String blpPath = saveFile(businessLegalProof, baseDir, "business_legal_proof");
            String panPath = saveFile(companyPanCard, baseDir, "company_pan_card");
            String gstPath = saveFile(gstRegistration, baseDir, "gst_registration");
            String bankPath = saveFile(bankPassbook, baseDir, "bank_passbook");
            String certPath = saveFile(certOfIncorp, baseDir, "cert_of_incorp");

            VendorDocuments docs = new VendorDocuments();
            docs.setVendorId(vendorId);
            docs.setBusinessLegalProof(blpPath);
            docs.setCompanyPanCard(panPath);
            docs.setGstRegistration(gstPath);
            docs.setBankPassbook(bankPath);
            docs.setCertOfIncorp(certPath);

            documentsRepo.save(docs);

            return ResponseEntity.ok().body("{\"message\":\"Documents uploaded successfully!\"}");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("{\"error\":\"Failed to save files.\"}");
        }
    }

    private String saveFile(MultipartFile file, String dir, String prefix) throws IOException {
        if (file.isEmpty()) return null;
        String filename = prefix + "_" + System.currentTimeMillis() + ".pdf";
        Path path = Paths.get(dir + filename);
        file.transferTo(path);
        return path.toString();
    }
}