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
        System.out.println("=== PROJECT CREATION REQUEST ===");
        System.out.println("Received project creation request");
        System.out.println("Project Title: " + (dto != null ? dto.getProjectTitle() : "null"));
        System.out.println("Company ID: " + (dto != null ? dto.getCompanyId() : "null"));
        
        try {
            // Validate DTO
            if (dto == null) {
                System.out.println("ERROR: DTO is null");
                return ResponseEntity.badRequest().body("Request body is missing");
            }

            // Validate required fields
            if (dto.getProjectTitle() == null || dto.getProjectTitle().trim().isEmpty()) {
                System.out.println("ERROR: Project title is missing");
                return ResponseEntity.badRequest().body("Project title is required");
            }
            if (dto.getCompanyId() == null) {
                System.out.println("ERROR: Company ID is missing");
                return ResponseEntity.badRequest().body("Company ID is required");
            }
            if (dto.getServiceCategory() == null || dto.getServiceCategory().trim().isEmpty()) {
                System.out.println("ERROR: Service category is missing");
                return ResponseEntity.badRequest().body("Service category is required");
            }
            if (dto.getStartDate() == null) {
                System.out.println("ERROR: Start date is missing");
                return ResponseEntity.badRequest().body("Start date is required");
            }
            if (dto.getEndDate() == null) {
                System.out.println("ERROR: End date is missing");
                return ResponseEntity.badRequest().body("End date is required");
            }
            if (dto.getPriority() == null || dto.getPriority().trim().isEmpty()) {
                System.out.println("ERROR: Priority is missing");
                return ResponseEntity.badRequest().body("Priority is required");
            }

            System.out.println("All validations passed, creating project...");

            // Create project
            Integer projectId = projectService.createProject(dto);

            // Return success response
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Project created successfully");
            response.put("projectId", projectId);
            response.put("status", "PENDING");

            System.out.println("Project created successfully with ID: " + projectId);
            System.out.println("Returning response: " + response);
            
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.out.println("ERROR creating project: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to create project");
            errorResponse.put("message", e.getMessage());
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<?> getProjectsByCompany(@PathVariable Integer companyId) {
        System.out.println("=== GET PROJECTS REQUEST ===");
        System.out.println("Fetching projects for company ID: " + companyId);
        
        try {
            List<ClientProjectInfo> projects = projectService.getProjectsByCompanyId(companyId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("projects", projects);
            response.put("count", projects.size());
            
            System.out.println("Returning " + projects.size() + " projects");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.out.println("ERROR fetching projects: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to fetch projects: " + e.getMessage());
        }
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable Integer projectId) {
        System.out.println("=== GET PROJECT BY ID REQUEST ===");
        System.out.println("Fetching project with ID: " + projectId);
        
        try {
            ClientProjectInfo project = projectService.getProjectById(projectId);
            System.out.println("Found project: " + project.getProjectTitle());
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
        System.out.println("=== UPDATE PROJECT STATUS REQUEST ===");
        System.out.println("Updating project " + projectId + " status");
        
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

            System.out.println("Project status updated successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.out.println("ERROR updating project status: " + e.getMessage());
            return ResponseEntity.status(500).body("Failed to update project status: " + e.getMessage());
        }
    }

    // Test endpoint to verify controller is working
    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        System.out.println("=== PROJECT CONTROLLER TEST ===");
        return ResponseEntity.ok("Project controller is working!");
    }

    // Enhanced test endpoint
    @GetMapping("/test-access")
    public ResponseEntity<Map<String, Object>> testAccess() {
        System.out.println("=== PROJECT CONTROLLER ACCESS TEST ===");
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Project controller is accessible!");
        response.put("timestamp", new java.util.Date());
        response.put("endpoints", java.util.Arrays.asList(
            "/api/client/projects/create",
            "/api/client/projects/company/{id}",
            "/api/client/projects/{id}",
            "/api/client/projects/test"
        ));
        return ResponseEntity.ok(response);
    }
}
