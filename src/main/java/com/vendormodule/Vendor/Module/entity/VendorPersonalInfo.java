package com.vendormodule.Vendor.Module.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vendor_personal_info")
public class VendorPersonalInfo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Column(name = "contact_person")
    private String contactPerson;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "address", columnDefinition = "TEXT")
    private String address;
    
    @Column(name = "gstin")
    private String gstin;
    
    @Column(name = "is_msme")
    private String isMsme;
    
    // Default constructor
    public VendorPersonalInfo() {}
    
    // Constructor with all fields
    public VendorPersonalInfo(String name, LocalDate dateOfBirth, String contactPerson, 
                             String phone, String email, String address, String gstin, String isMsme) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.contactPerson = contactPerson;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.gstin = gstin;
        this.isMsme = isMsme;
    }
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getContactPerson() {
        return contactPerson;
    }
    
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getGstin() {
        return gstin;
    }
    
    public void setGstin(String gstin) {
        this.gstin = gstin;
    }
    
    public String getIsMsme() {
        return isMsme;
    }
    
    public void setIsMsme(String isMsme) {
        this.isMsme = isMsme;
    }
    
    @Override
    public String toString() {
        return "VendorPersonalInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", contactPerson='" + contactPerson + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", gstin='" + gstin + '\'' +
                ", isMsme='" + isMsme + '\'' +
                '}';
    }
}
