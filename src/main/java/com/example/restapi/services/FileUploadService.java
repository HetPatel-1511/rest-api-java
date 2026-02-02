package com.example.restapi.services;

import com.example.restapi.dto.request.CreateEmployeeDTO;
import com.example.restapi.dto.request.UpdateEmployeeDTO;
import com.example.restapi.dto.response.EmployeeResponseDTO;
import com.example.restapi.entities.Employee;
import com.example.restapi.mapper.EntityMapper;
import com.example.restapi.repos.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class FileUploadService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    public String saveImage(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = file.getOriginalFilename();
        Path filePath = uploadPath.resolve(UUID.randomUUID()+"-"+fileName);
        Files.copy(file.getInputStream(), filePath);

        return filePath.toString();
    }
}
