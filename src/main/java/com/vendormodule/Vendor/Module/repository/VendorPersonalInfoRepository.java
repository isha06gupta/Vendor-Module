package com.vendormodule.Vendor.Module.repository;

import com.vendormodule.Vendor.Module.entity.VendorPersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorPersonalInfoRepository extends JpaRepository<VendorPersonalInfo, Integer> {
    Optional<VendorPersonalInfo> findByEmail(String email);
    Optional<VendorPersonalInfo> findByPhone(String phone);
}
