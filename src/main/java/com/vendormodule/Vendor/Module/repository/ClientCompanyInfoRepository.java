package com.vendormodule.Vendor.Module.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vendormodule.Vendor.Module.entity.ClientCompanyInfo;

@Repository
public interface ClientCompanyInfoRepository extends JpaRepository<ClientCompanyInfo, Integer> {
}
