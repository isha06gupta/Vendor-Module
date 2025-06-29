package com.vendormodule.Vendor.Module.repository;

import com.vendormodule.Vendor.Module.entity.VendorDocuments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorDocumentsRepository extends JpaRepository<VendorDocuments, Integer> {
    Optional<VendorDocuments> findByVendorPersonalInfoId(Integer vendorPersonalInfoId);
}
