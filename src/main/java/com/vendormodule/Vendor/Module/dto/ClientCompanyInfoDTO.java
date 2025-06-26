package com.vendormodule.Vendor.Module.dto;

public class ClientCompanyInfoDTO {
    private String companyName;
    private String industryType;
    private String companyAddress;
    private String officialEmail;
    private int yearOfEstablishment;
    private String isMsme;
    private String gstin;
    private String website;

    // Getters and setters
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getIndustryType() { return industryType; }
    public void setIndustryType(String industryType) { this.industryType = industryType; }

    public String getCompanyAddress() { return companyAddress; }
    public void setCompanyAddress(String companyAddress) { this.companyAddress = companyAddress; }

    public String getOfficialEmail() { return officialEmail; }
    public void setOfficialEmail(String officialEmail) { this.officialEmail = officialEmail; }

    public int getYearOfEstablishment() { return yearOfEstablishment; }
    public void setYearOfEstablishment(int yearOfEstablishment) { this.yearOfEstablishment = yearOfEstablishment; }

    public String getIsMsme() { return isMsme; }
    public void setIsMsme(String isMsme) { this.isMsme = isMsme; }

    public String getGstin() { return gstin; }
    public void setGstin(String gstin) { this.gstin = gstin; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
}
