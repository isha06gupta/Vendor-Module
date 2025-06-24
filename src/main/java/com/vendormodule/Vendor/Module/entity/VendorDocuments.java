package com.vendormodule.Vendor.Module.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "vendor_documents")
@Data
public class VendorDocuments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vendor_id")
    private Long vendorId;

    @Column(name = "business_legal_proof")
    private String businessLegalProof;

    @Column(name = "company_pan_card")
    private String companyPanCard;

    @Column(name = "gst_registration")
    private String gstRegistration;

    @Column(name = "bank_passbook")
    private String bankPassbook;

    @Column(name = "cert_of_incorp")
    private String certOfIncorp;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt = LocalDateTime.now();
}