package com.vendormodule.Vendor.Module.dto;

public class ClientRegistrationDTO {
    private ClientInfoDTO clientInfo;
    private CompanyInfoDTO companyInfo;

    // Constructors
    public ClientRegistrationDTO() {}

    public ClientRegistrationDTO(ClientInfoDTO clientInfo, CompanyInfoDTO companyInfo) {
        this.clientInfo = clientInfo;
        this.companyInfo = companyInfo;
    }

    // Getters and Setters
    public ClientInfoDTO getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfoDTO clientInfo) {
        this.clientInfo = clientInfo;
    }

    public CompanyInfoDTO getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(CompanyInfoDTO companyInfo) {
        this.companyInfo = companyInfo;
    }

    // Inner DTOs
    public static class ClientInfoDTO {
        private String fullName;
        private String designation;
        private String officialEmail;
        private String mobileNumber;
        private String alternateContact;

        // Constructors
        public ClientInfoDTO() {}

        // Getters and Setters
        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public String getOfficialEmail() {
            return officialEmail;
        }

        public void setOfficialEmail(String officialEmail) {
            this.officialEmail = officialEmail;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public String getAlternateContact() {
            return alternateContact;
        }

        public void setAlternateContact(String alternateContact) {
            this.alternateContact = alternateContact;
        }
    }

    public static class CompanyInfoDTO {
        private String companyName;
        private String industryType;
        private String companyAddress;
        private String officialEmail;
        private Integer yearOfEstablishment;
        private String isMsme;
        private String gstin;
        private String website;

        // Constructors
        public CompanyInfoDTO() {}

        // Getters and Setters
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
    }
}
