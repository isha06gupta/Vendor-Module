package com.ceil.vendor.vendorportal.model;

import jakarta.persistence.*; 
import lombok.AllArgsConstructor; 
import lombok.Data;             
import lombok.NoArgsConstructor;  
import java.time.LocalDateTime;

@Entity 
@Table(name = "vendors") 
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class vendor {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id; 

    @Column(nullable = false) 
    private String vendorName;

    @Column(nullable = false, length = 500) 
    private String address;

    private String contactPerson; 

    @Column(nullable = false, unique = true) 
    private String email;

    @Column(length = 20) 
    private String mobileNumber;

    @Column(length = 255) 
    private String website;

    @Column(nullable = false, unique = true, length = 15) 
    private String gstin;

    @Column(length = 10) 
    private String panNumber;

    @Column(length = 20) 
    private String landlinePhone;

    @Column(length = 20) 
    private String faxNumber;

    @Column(length = 50) 
    private String businessType;

    @Column(length = 50) 
    private String organizationType;

    private Integer establishmentYear; 

    @Column(length = 100) 
    private String vendorCategory;

    @Column(length = 500) 
    private String keyOfferings;

    private Boolean isMsme; 

    @Column(length = 30) 
    private String udyamRegNo;

    @Column(length = 200) 
    private String howDidYouHearAboutUs;

    @Column(length = 500) 
    private String standardItems;

    private Boolean incorporationCertUploaded = false;
    private Boolean panCardUploaded = false;
    private Boolean gstCertUploaded = false;
    private Boolean bankProofUploaded = false;
    private Boolean certIncorporationPartnershipUploaded = false;

    private String incorporationCertPath;
    private String panCardPath; 
    private String gstCertPath;
    private String bankProofPath;
    private String certIncorporationPartnershipPath;

    private LocalDateTime registrationDate; 

    @Column(nullable = false, length = 50) 
    private String status = "PENDING_DOCUMENTS"; 

    @PrePersist
    protected void onCreate() {
        if (registrationDate == null) {
            registrationDate = LocalDateTime.now();
        }
    }
}