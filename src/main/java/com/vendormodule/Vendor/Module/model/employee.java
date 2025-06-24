package com.vendormodule.Vendor.Module.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "employees") 
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class employee { 

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id; 

    @Column(nullable = false, unique = true) 
    private String email; 

    @Column(nullable = false) 
    private String password; 

}
