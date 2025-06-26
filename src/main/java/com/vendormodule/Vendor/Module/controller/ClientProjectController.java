package com.vendormodule.Vendor.Module.controller;

import com.vendormodule.Vendor.Module.service.ClientProjectService;
import com.vendormodule.Vendor.Module.dto.ClientProjectDTO;
import com.vendormodule.Vendor.Module.entity.ClientProjectInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/client/projects")
@CrossOrigin(origins = "*")
public class ClientProjectController {

    @Autowired
    private ClientProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<?> createProject(@RequestBody ClientProjectDTO dto) {
        System.out.println("=== Project Creation Request ===");
        System.out.println("Received DTO: " + dto.getProjectTitle());
        
        try {
            // Validate required fields
            if (dto.getProjectTitle() == null || dto.getProjectTitle().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Project title is required");
            }
            if (dto.getCompanyId() == null) {
                return ResponseEntity.badRequest().body("Company ID is required");
            }
            if (dto.getServiceCategory() == null || dto.getServiceCategory().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Service category is required");
            }
            if (dto.getStartDate() == null) {
                return ResponseEntity.badRequest().body("Start date is required");
            }
            if (dto.getEndDate() == null) {
                return ResponseEntity.badRequest().body("End date is required");
            }
            if (dto.getPriority() == null || dto.getPriority().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Priority is required");
            }

            // Create project
            Integer projectId = projectService.createProject(dto);

            // Return success response
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Project created successfully");
            response.put("projectId", projectId);
            response.put("status", "PENDING");

            System.out.println("Project created successfully with ID: " + projectId);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.out.println("ERROR creating project: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to create project: " + e.getMessage());
        }
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<?> getProjectsByCompany(@PathVariable Integer companyId) {
        try {
            List<ClientProjectInfo> projects = projectService.getProjectsByCompanyId(companyId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("projects", projects);
            response.put("count", projects.size());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.out.println("ERROR fetching projects: " + e.getMessage());
            return ResponseEntity.status(500).body("Failed to fetch projects: " + e.getMessage());
        }
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable Integer projectId) {
        try {
            ClientProjectInfo project = projectService.getProjectById(projectId);
            return ResponseEntity.ok(project);
            
        } catch (Exception e) {
            System.out.println("ERROR fetching project: " + e.getMessage());
            return ResponseEntity.status(500).body("Failed to fetch project: " + e.getMessage());
        }
    }

    @PutMapping("/{projectId}/status")
    public ResponseEntity<?> updateProjectStatus(
            @PathVariable Integer projectId, 
            @RequestBody Map<String, String> statusUpdate) {
        try {
            String newStatus = statusUpdate.get("status");
            if (newStatus == null || newStatus.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Status is required");
            }

            projectService.updateProjectStatus(projectId, newStatus);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Project status updated successfully");
            response.put("projectId", projectId);
            response.put("newStatus", newStatus);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.out.println("ERROR updating project status: " + e.getMessage());
            return ResponseEntity.status(500).body("Failed to update project status: " + e.getMessage());
        }
    }
}
