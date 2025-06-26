package com.vendormodule.Vendor.Module.filestorage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {
    
    @Value("${file.upload-dir:D:/OTHERS/HTML Files/Vendor Module/uploads}")
    private String uploadDir;

    public String storeFile(MultipartFile file, String subfolder) throws IOException {
        System.out.println("=== FileStorageService Debug ===");
        System.out.println("Upload directory: " + uploadDir);
        System.out.println("Subfolder: " + subfolder);
        System.out.println("File name: " + file.getOriginalFilename());
        System.out.println("File size: " + file.getSize());
        System.out.println("File content type: " + file.getContentType());
        
        try {
            // Check if file is empty
            if (file.isEmpty()) {
                throw new IOException("File is empty: " + file.getOriginalFilename());
            }

            // Create upload directory if it doesn't exist
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            System.out.println("Creating upload path: " + uploadPath);
            
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                System.out.println("Created upload directory: " + uploadPath);
            } else {
                System.out.println("Upload directory already exists: " + uploadPath);
            }
            
            // Create subfolder
            Path subfolderPath = uploadPath.resolve(subfolder);
            System.out.println("Creating subfolder path: " + subfolderPath);
            
            if (!Files.exists(subfolderPath)) {
                Files.createDirectories(subfolderPath);
                System.out.println("Created subfolder: " + subfolderPath);
            } else {
                System.out.println("Subfolder already exists: " + subfolderPath);
            }

            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.trim().isEmpty()) {
                originalFilename = "file_" + System.currentTimeMillis();
            }
            
            String filename = System.currentTimeMillis() + "_" + originalFilename;
            Path targetLocation = subfolderPath.resolve(filename);
            System.out.println("Target file location: " + targetLocation);

            // Copy file to target location
            Files.copy(file.getInputStream(), targetLocation);
            System.out.println("File copied successfully to: " + targetLocation);
            
            // Verify file was created
            if (Files.exists(targetLocation)) {
                long fileSize = Files.size(targetLocation);
                System.out.println("File verification: exists=" + Files.exists(targetLocation) + ", size=" + fileSize);
            } else {
                throw new IOException("File was not created at: " + targetLocation);
            }
            
            return targetLocation.toString();
            
        } catch (Exception e) {
            System.out.println("ERROR in FileStorageService: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            e.printStackTrace();
            throw new IOException("Failed to store file: " + e.getMessage(), e);
        }
    }
}
