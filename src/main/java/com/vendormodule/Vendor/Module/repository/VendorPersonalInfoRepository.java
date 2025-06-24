package com.vendormodule.Vendor.Module.repository;


import com.vendormodule.Vendor.Module.entity.VendorPersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorPersonalInfoRepository extends JpaRepository<VendorPersonalInfo, Long> {
}