package com.vendormodule.Vendor.Module.controller;


import com.vendormodule.Vendor.Module.entity.VendorPersonalInfo;
import com.vendormodule.Vendor.Module.service.VendorPersonalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendor")
@CrossOrigin(origins = "*") // For development/testing
public class VendorPersonalInfoController {
    @Autowired
    private VendorPersonalInfoService service;

    @PostMapping("/personal-info")
    public VendorPersonalInfo savePersonalInfo(@RequestBody VendorPersonalInfo info) {
        return service.save(info);
    }
}