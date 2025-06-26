package com.vendormodule.Vendor.Module.dto;

import java.time.LocalDate;
import java.util.List;

public class ClientProjectDTO {
    private String projectTitle;
    private String projectDescription;
    private String serviceCategory;
    private LocalDate startDate;
    private LocalDate endDate;
    private String projectLocation;
    private Double budget;
    private String priority;
    private List<String> preferredVendors;
    private Integer companyId;
    
    // Constructors
    public ClientProjectDTO() {}
    
    public ClientProjectDTO(String projectTitle, String projectDescription, String serviceCategory,
                          LocalDate startDate, LocalDate endDate, String projectLocation,
                          Double budget, String priority, List<String> preferredVendors, Integer companyId) {
        this.projectTitle = projectTitle;
        this.projectDescription = projectDescription;
        this.serviceCategory = serviceCategory;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectLocation = projectLocation;
        this.budget = budget;
        this.priority = priority;
        this.preferredVendors = preferredVendors;
        this.companyId = companyId;
    }
    
    // Getters and Setters
    public String getProjectTitle() {
        return projectTitle;
    }
    
    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }
    
    public String getProjectDescription() {
        return projectDescription;
    }
    
    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }
    
    public String getServiceCategory() {
        return serviceCategory;
    }
    
    public void setServiceCategory(String serviceCategory) {
        this.serviceCategory = serviceCategory;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    public String getProjectLocation() {
        return projectLocation;
    }
    
    public void setProjectLocation(String projectLocation) {
        this.projectLocation = projectLocation;
    }
    
    public Double getBudget() {
        return budget;
    }
    
    public void setBudget(Double budget) {
        this.budget = budget;
    }
    
    public String getPriority() {
        return priority;
    }
    
    public void setPriority(String priority) {
        this.priority = priority;
    }
    
    public List<String> getPreferredVendors() {
        return preferredVendors;
    }
    
    public void setPreferredVendors(List<String> preferredVendors) {
        this.preferredVendors = preferredVendors;
    }
    
    public Integer getCompanyId() {
        return companyId;
    }
    
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}

