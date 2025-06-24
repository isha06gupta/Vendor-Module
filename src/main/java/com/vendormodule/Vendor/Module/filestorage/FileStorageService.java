package com.vendormodule.Vendor.Module.filestorage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class FileStorageService {
    public String storeFile(MultipartFile file, String subfolder) throws IOException {
        // Implement file storage logic here, for now just return a dummy path
        return "/path/to/stored/" + subfolder + "/" + file.getOriginalFilename();
    }
}