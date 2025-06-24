package com.vendormodule.Vendor.Module.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.vendormodule.Vendor.Module.entity.VendorDocuments; 
public interface VendorDocumentsRepository extends JpaRepository<VendorDocuments, Long> {
}
