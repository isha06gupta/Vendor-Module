package com.vendormodule.Vendor.Module.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "vendor_documents")
public class VendorDocuments {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "incorporation_cert_path")
    private String incorporationCertPath;
    
    @Column(name = "pan_card_path")
    private String panCardPath;
    
    @Column(name = "gst_cert_path")
    private String gstCertPath;
    
    @Column(name = "bank_passbook_path")
    private String bankPassbookPath;
    
    @Column(name = "certification_path")
    private String certificationPath;
    
    @OneToOne
    @JoinColumn(name = "vendor_personal_info_id", referencedColumnName = "id")
    private VendorPersonalInfo vendorPersonalInfo;
    
    // Default constructor
    public VendorDocuments() {}
    
    // Constructor with all fields
    public VendorDocuments(String incorporationCertPath, String panCardPath, String gstCertPath, 
                          String bankPassbookPath, String certificationPath, VendorPersonalInfo vendorPersonalInfo) {
        this.incorporationCertPath = incorporationCertPath;
        this.panCardPath = panCardPath;
        this.gstCertPath = gstCertPath;
        this.bankPassbookPath = bankPassbookPath;
        this.certificationPath = certificationPath;
        this.vendorPersonalInfo = vendorPersonalInfo;
    }
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getIncorporationCertPath() {
        return incorporationCertPath;
    }
    
    public void setIncorporationCertPath(String incorporationCertPath) {
        this.incorporationCertPath = incorporationCertPath;
    }
    
    public String getPanCardPath() {
        return panCardPath;
    }
    
    public void setPanCardPath(String panCardPath) {
        this.panCardPath = panCardPath;
    }
    
    public String getGstCertPath() {
        return gstCertPath;
    }
    
    public void setGstCertPath(String gstCertPath) {
        this.gstCertPath = gstCertPath;
    }
    
    public String getBankPassbookPath() {
        return bankPassbookPath;
    }
    
    public void setBankPassbookPath(String bankPassbookPath) {
        this.bankPassbookPath = bankPassbookPath;
    }
    
    public String getCertificationPath() {
        return certificationPath;
    }
    
    public void setCertificationPath(String certificationPath) {
        this.certificationPath = certificationPath;
    }
    
    public VendorPersonalInfo getVendorPersonalInfo() {
        return vendorPersonalInfo;
    }
    
    public void setVendorPersonalInfo(VendorPersonalInfo vendorPersonalInfo) {
        this.vendorPersonalInfo = vendorPersonalInfo;
    }
    
    @Override
    public String toString() {
        return "VendorDocuments{" +
                "id=" + id +
                ", incorporationCertPath='" + incorporationCertPath + '\'' +
                ", panCardPath='" + panCardPath + '\'' +
                ", gstCertPath='" + gstCertPath + '\'' +
                ", bankPassbookPath='" + bankPassbookPath + '\'' +
                ", certificationPath='" + certificationPath + '\'' +
                ", vendorPersonalInfo=" + (vendorPersonalInfo != null ? vendorPersonalInfo.getId() : null) +
                '}';
    }
}
