package com.vendormodule.Vendor.Module.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "client_info")
public class ClientInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "full_name", nullable = false)
    private String fullName;
    
    @Column(name = "designation")
    private String designation;
    
    @Column(name = "official_email", nullable = false)
    private String officialEmail;
    
    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;
    
    @Column(name = "alternate_contact")
    private String alternateContact;
    
    // One-to-One relationship with ClientCompanyInfo
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ClientCompanyInfo companyInfo;
    
    // Constructors
    public ClientInfo() {}
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getDesignation() {
        return designation;
    }
    
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    
    public String getOfficialEmail() {
        return officialEmail;
    }
    
    public void setOfficialEmail(String officialEmail) {
        this.officialEmail = officialEmail;
    }
    
    public String getMobileNumber() {
        return mobileNumber;
    }
    
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    
    public String getAlternateContact() {
        return alternateContact;
    }
    
    public void setAlternateContact(String alternateContact) {
        this.alternateContact = alternateContact;
    }
    
    public ClientCompanyInfo getCompanyInfo() {
        return companyInfo;
    }
    
    public void setCompanyInfo(ClientCompanyInfo companyInfo) {
        this.companyInfo = companyInfo;
    }
}
