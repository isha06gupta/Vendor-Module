package com.vendormodule.Vendor.Module.service;


import com.vendormodule.Vendor.Module.entity.VendorPersonalInfo;
import com.vendormodule.Vendor.Module.repository.VendorPersonalInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendorPersonalInfoService {
    @Autowired
    private VendorPersonalInfoRepository repository;

    public VendorPersonalInfo save(VendorPersonalInfo info) {
        return repository.save(info);
    }
}