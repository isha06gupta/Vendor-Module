package com.vendormodule.Vendor.Module.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "client_company_info")
public class ClientCompanyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String companyName;
    private String industryType;
    private String companyAddress;
    private String officialEmail;
    private Integer yearOfEstablishment;
    private String isMsme;
    private String gstin;
    private String website;
    
    // Document path fields with proper annotations
    @Column(name = "company_pan_card_path")
    private String companyPanCardPath;

    @Column(name = "registration_proof_path")
    private String registrationProofPath;

    @Column(name = "work_order_path")
    private String workOrderPath;

    @Column(name = "uploaded_at", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp uploadedAt;
    
    @OneToOne
    @JoinColumn(name = "client_id")
    private ClientInfo client;
    
    // Constructors
    public ClientCompanyInfo() {}
    
    // Getters and Setters
    public Integer getId() { 
        return id; 
    }
    
    public void setId(Integer id) { 
        this.id = id; 
    }
    
    public String getCompanyName() { 
        return companyName; 
    }
    
    public void setCompanyName(String companyName) { 
        this.companyName = companyName; 
    }

    public String getIndustryType() { 
        return industryType; 
    }
    
    public void setIndustryType(String industryType) { 
        this.industryType = industryType; 
    }

    public String getCompanyAddress() { 
        return companyAddress; 
    }
    
    public void setCompanyAddress(String companyAddress) { 
        this.companyAddress = companyAddress; 
    }

    public String getOfficialEmail() { 
        return officialEmail; 
    }
    
    public void setOfficialEmail(String officialEmail) { 
        this.officialEmail = officialEmail; 
    }

    public Integer getYearOfEstablishment() { 
        return yearOfEstablishment; 
    }
    
    public void setYearOfEstablishment(Integer yearOfEstablishment) { 
        this.yearOfEstablishment = yearOfEstablishment; 
    }

    public String getIsMsme() { 
        return isMsme; 
    }
    
    public void setIsMsme(String isMsme) { 
        this.isMsme = isMsme; 
    }

    public String getGstin() { 
        return gstin; 
    }
    
    public void setGstin(String gstin) { 
        this.gstin = gstin; 
    }

    public String getWebsite() { 
        return website; 
    }
    
    public void setWebsite(String website) { 
        this.website = website; 
    }

    public ClientInfo getClient() { 
        return client; 
    }
    
    public void setClient(ClientInfo client) { 
        this.client = client; 
    }
    
    // Document path getters and setters
    public String getCompanyPanCardPath() { 
        return companyPanCardPath; 
    }
    
    public void setCompanyPanCardPath(String companyPanCardPath) { 
        this.companyPanCardPath = companyPanCardPath; 
    }
    
    public String getRegistrationProofPath() { 
        return registrationProofPath; 
    }
    
    public void setRegistrationProofPath(String registrationProofPath) { 
        this.registrationProofPath = registrationProofPath; 
    }
    
    public String getWorkOrderPath() { 
        return workOrderPath; 
    }
    
    public void setWorkOrderPath(String workOrderPath) { 
        this.workOrderPath = workOrderPath; 
    }

    public Timestamp getUploadedAt() { 
        return uploadedAt; 
    }
    
    public void setUploadedAt(Timestamp uploadedAt) { 
        this.uploadedAt = uploadedAt; 
    }
}
