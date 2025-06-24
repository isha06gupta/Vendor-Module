package com.vendormodule.Vendor.Module.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vendormodule.Vendor.Module.model.Vendor;

import java.util.Optional; 

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

    Optional<Vendor> findByEmail(String email);
    Optional<Vendor> findByGstin(String gstin);

    
}
