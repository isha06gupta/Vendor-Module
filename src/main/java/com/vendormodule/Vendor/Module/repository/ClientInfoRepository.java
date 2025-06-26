package com.vendormodule.Vendor.Module.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vendormodule.Vendor.Module.entity.ClientInfo;

public interface ClientInfoRepository extends JpaRepository<ClientInfo, Integer> {}