package com.vendormodule.Vendor.Module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vendormodule.Vendor.Module.repository.ClientProjectInfoRepository;
import com.vendormodule.Vendor.Module.repository.ClientCompanyInfoRepository;
import com.vendormodule.Vendor.Module.dto.ClientProjectDTO;
import com.vendormodule.Vendor.Module.entity.ClientProjectInfo;
import com.vendormodule.Vendor.Module.entity.ClientCompanyInfo;

import java.util.List;

@Service
@Transactional
public class ClientProjectService {
    
    @Autowired
    private ClientProjectInfoRepository projectRepository;
    
    @Autowired
    private ClientCompanyInfoRepository companyRepository;
    
    public Integer createProject(ClientProjectDTO dto) {
        System.out.println("=== PROJECT SERVICE DEBUG ===");
        System.out.println("Creating project: " + dto.getProjectTitle());
        System.out.println("For company ID: " + dto.getCompanyId());
        
        try {
            // Validate company exists
            if (dto.getCompanyId() == null) {
                throw new RuntimeException("Company ID is required");
            }
            
            System.out.println("Looking for company with ID: " + dto.getCompanyId());
            ClientCompanyInfo company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found with ID: " + dto.getCompanyId()));
            
            System.out.println("Found company: " + company.getCompanyName());
            
            // Create project entity
            ClientProjectInfo project = new ClientProjectInfo();
            project.setProjectTitle(dto.getProjectTitle());
            project.setProjectDescription(dto.getProjectDescription());
            project.setServiceCategory(dto.getServiceCategory());
            project.setStartDate(dto.getStartDate());
            project.setEndDate(dto.getEndDate());
            project.setProjectLocation(dto.getProjectLocation());
            project.setBudget(dto.getBudget());
            project.setPriority(dto.getPriority());
            
            // Convert preferred vendors list to comma-separated string
            if (dto.getPreferredVendors() != null && !dto.getPreferredVendors().isEmpty()) {
                String vendorsString = String.join(",", dto.getPreferredVendors());
                project.setPreferredVendors(vendorsString);
                System.out.println("Set preferred vendors: " + vendorsString);
            }
            
            // Link to company
            project.setClientCompanyInfo(company);
            project.setStatus("PENDING");
            
            System.out.println("Saving project to database...");
            
            // Save project
            ClientProjectInfo savedProject = projectRepository.save(project);
            
            System.out.println("Project saved successfully with ID: " + savedProject.getId());
            System.out.println("Project title: " + savedProject.getProjectTitle());
            System.out.println("Project status: " + savedProject.getStatus());
            System.out.println("Linked to company: " + savedProject.getClientCompanyInfo().getCompanyName());
            
            return savedProject.getId();
            
        } catch (Exception e) {
            System.out.println("ERROR creating project: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to create project: " + e.getMessage(), e);
        }
    }
    
    public List<ClientProjectInfo> getProjectsByCompanyId(Integer companyId) {
        System.out.println("=== FETCHING PROJECTS DEBUG ===");
        System.out.println("Fetching projects for company ID: " + companyId);
        
        try {
            List<ClientProjectInfo> projects = projectRepository.findByCompanyId(companyId);
            System.out.println("Found " + projects.size() + " projects");
            
            for (ClientProjectInfo project : projects) {
                System.out.println("- Project: " + project.getProjectTitle() + " (ID: " + project.getId() + ")");
            }
            
            return projects;
        } catch (Exception e) {
            System.out.println("ERROR fetching projects: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch projects: " + e.getMessage(), e);
        }
    }
    
    public ClientProjectInfo getProjectById(Integer projectId) {
        return projectRepository.findById(projectId)
            .orElseThrow(() -> new RuntimeException("Project not found with ID: " + projectId));
    }
    
    public void updateProjectStatus(Integer projectId, String status) {
        try {
            ClientProjectInfo project = getProjectById(projectId);
            project.setStatus(status);
            projectRepository.save(project);
            System.out.println("Updated project " + projectId + " status to: " + status);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update project status: " + e.getMessage(), e);
        }
    }
}
