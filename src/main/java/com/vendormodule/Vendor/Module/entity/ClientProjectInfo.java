package com.vendormodule.Vendor.Module.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "client_project_info")
public class ClientProjectInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "project_title", nullable = false)
    private String projectTitle;
    
    @Column(name = "project_description", columnDefinition = "TEXT")
    private String projectDescription;
    
    @Column(name = "service_category", nullable = false)
    private String serviceCategory;
    
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    
    @Column(name = "project_location", nullable = false)
    private String projectLocation;
    
    @Column(name = "budget")
    private Double budget;
    
    @Column(name = "priority", nullable = false)
    private String priority;
    
    @Column(name = "preferred_vendors", columnDefinition = "TEXT")
    private String preferredVendors;
    
    @Column(name = "status", nullable = false)
    private String status = "PENDING";
    
    @Column(name = "created_at", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;
    
    @Column(name = "updated_at", insertable = false, updatable = true, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;
    
    // Foreign key to ClientCompanyInfo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private ClientCompanyInfo clientCompanyInfo;
    
    // Constructors
    public ClientProjectInfo() {}
    
    public ClientProjectInfo(String projectTitle, String projectDescription, String serviceCategory, 
                           LocalDate startDate, LocalDate endDate, String projectLocation, 
                           Double budget, String priority, String preferredVendors, 
                           ClientCompanyInfo clientCompanyInfo) {
        this.projectTitle = projectTitle;
        this.projectDescription = projectDescription;
        this.serviceCategory = serviceCategory;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectLocation = projectLocation;
        this.budget = budget;
        this.priority = priority;
        this.preferredVendors = preferredVendors;
        this.clientCompanyInfo = clientCompanyInfo;
    }
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
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
    
    public String getPreferredVendors() {
        return preferredVendors;
    }
    
    public void setPreferredVendors(String preferredVendors) {
        this.preferredVendors = preferredVendors;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public ClientCompanyInfo getClientCompanyInfo() {
        return clientCompanyInfo;
    }
    
    public void setClientCompanyInfo(ClientCompanyInfo clientCompanyInfo) {
        this.clientCompanyInfo = clientCompanyInfo;
    }
}
