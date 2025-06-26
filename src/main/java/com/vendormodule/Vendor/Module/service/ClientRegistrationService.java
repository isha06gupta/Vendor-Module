package com.vendormodule.Vendor.Module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vendormodule.Vendor.Module.repository.ClientInfoRepository;
import com.vendormodule.Vendor.Module.dto.ClientRegistrationDTO;
import com.vendormodule.Vendor.Module.entity.ClientInfo;
import com.vendormodule.Vendor.Module.entity.ClientCompanyInfo;

@Service
@Transactional
public class ClientRegistrationService {
    @Autowired
    private ClientInfoRepository clientRepo;

    public Integer registerClient(ClientRegistrationDTO dto) {
        System.out.println("=== SERVICE REGISTRATION DEBUG ===");
        
        try {
            // Create ClientInfo entity
            ClientInfo client = new ClientInfo();
            client.setFullName(dto.getClientInfo().getFullName());
            client.setDesignation(dto.getClientInfo().getDesignation());
            client.setOfficialEmail(dto.getClientInfo().getOfficialEmail());
            client.setMobileNumber(dto.getClientInfo().getMobileNumber());
            client.setAlternateContact(dto.getClientInfo().getAlternateContact());
            
            System.out.println("Created ClientInfo: " + client.getFullName());

            // Create ClientCompanyInfo entity
            ClientCompanyInfo company = new ClientCompanyInfo();
            company.setCompanyName(dto.getCompanyInfo().getCompanyName());
            company.setIndustryType(dto.getCompanyInfo().getIndustryType());
            company.setCompanyAddress(dto.getCompanyInfo().getCompanyAddress());
            company.setOfficialEmail(dto.getCompanyInfo().getOfficialEmail());
            company.setYearOfEstablishment(dto.getCompanyInfo().getYearOfEstablishment());
            company.setIsMsme(dto.getCompanyInfo().getIsMsme());
            company.setGstin(dto.getCompanyInfo().getGstin());
            company.setWebsite(dto.getCompanyInfo().getWebsite());

            System.out.println("Created ClientCompanyInfo: " + company.getCompanyName());

            // Link them - THIS IS CRITICAL
            company.setClient(client);
            client.setCompanyInfo(company);
            
            System.out.println("Linked client and company");

            // Save both - the cascade should handle both
            System.out.println("Saving to database...");
            ClientInfo savedClient = clientRepo.save(client);
            System.out.println("Saved client with ID: " + savedClient.getId());
            
            if (savedClient.getCompanyInfo() != null) {
                System.out.println("Company saved with ID: " + savedClient.getCompanyInfo().getId());
                return savedClient.getCompanyInfo().getId();
            } else {
                System.out.println("ERROR: Company info is null after save");
                throw new RuntimeException("Company info was not saved properly");
            }
            
        } catch (Exception e) {
            System.out.println("ERROR in service: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to register client: " + e.getMessage(), e);
        }
    }
}
