package com.vendormodule.Vendor.Module.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "vendor_personal_info")
public class VendorPersonalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    // Add more fields as per your form/table

    // Getters and setters
    // ...
}