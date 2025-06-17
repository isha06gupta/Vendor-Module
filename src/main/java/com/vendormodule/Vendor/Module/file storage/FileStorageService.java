package com.ceil.vendor.vendorportal.filestorage;

import org.springframework.beans.factory.annotation.Value; 
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import jakarta.annotation.PostConstruct; 

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID; 

@Service
public class FileStorageService {

    @Value("${file.upload-dir}") 
    private String uploadDir;
    private Path fileStorageLocation; 
    @PostConstruct 
    public void init() {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation); 
        } catch (IOException ex) {
            throw new RuntimeException("Could not create the upload directory!", ex);
        }
    }

    public String storeFile(MultipartFile file, String subfolder) throws IOException {
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || originalFileName.contains("..")) {
            throw new IOException("Sorry! Filename contains invalid path sequence " + originalFileName);
        }

        // Generate a unique file name to prevent clashes
        String fileExtension = "";
        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex > 0) {
            fileExtension = originalFileName.substring(dotIndex);
        }
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        // Construct the target path (e.g., upload-dir/vendorId/uniqueFileName.pdf)
        Path targetLocation = this.fileStorageLocation.resolve(subfolder).resolve(uniqueFileName);

        Files.createDirectories(targetLocation.getParent());

        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        return targetLocation.toString();
    }
}
