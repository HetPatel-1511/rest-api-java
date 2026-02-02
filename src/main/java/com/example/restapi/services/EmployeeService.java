package com.example.restapi.services;

import com.example.restapi.dto.request.CreateEmployeeDTO;
import com.example.restapi.dto.request.UpdateEmployeeDTO;
import com.example.restapi.dto.response.EmployeeResponseDTO;
import com.example.restapi.entities.Employee;
import com.example.restapi.mapper.EntityMapper;
import com.example.restapi.repos.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepo;
    private final EntityMapper entityMapper;
    private final FileUploadService fileUploadService;

    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo, EntityMapper entityMapper, FileUploadService fileUploadService){
        this.employeeRepo = employeeRepo;
        this.entityMapper = entityMapper;
        this.fileUploadService = fileUploadService;
    }

    public List<EmployeeResponseDTO> getAll() {
        return entityMapper.toEmployeeResponseDTOList(employeeRepo.findAll());
    }

    public EmployeeResponseDTO getByID(Long id) {
        return entityMapper.toEmployeeResponseDTO(employeeRepo.findById(id).orElse(null));
    }

    @Transactional
    public EmployeeResponseDTO create(CreateEmployeeDTO dto) {
        if (employeeRepo.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        Employee employee = entityMapper.toEmployee(dto);
        employee.setIs_active(true);
        employeeRepo.save(employee);
        return entityMapper.toEmployeeResponseDTO(employee);
    }

    @Transactional
    public EmployeeResponseDTO update(Long id, UpdateEmployeeDTO dto) {
        Employee existing = employeeRepo.findByUsername(dto.getUsername());
        if (existing!=null && existing.getId()!=id) {
            throw new RuntimeException("Username already exists");
        }
        Employee employee = employeeRepo.findById(id).orElse(null);
        if (employee==null) {
            throw new RuntimeException("Employee not found");
        }
        employee.setName(dto.getName());
        employee.setUsername(dto.getUsername());
        employeeRepo.save(employee);
        return entityMapper.toEmployeeResponseDTO(employee);
    }

    @Transactional
    public EmployeeResponseDTO updateIsActive(Long id, String is_active) {
        Employee employee = employeeRepo.findById(id).orElse(null);
        if (employee==null) {
            throw new RuntimeException("Employee not found");
        }
        employee.setIs_active(is_active.equals("true") || is_active.equals("True") || is_active.equals("1"));
        employeeRepo.save(employee);
        return entityMapper.toEmployeeResponseDTO(employee);
    }

    @Transactional
    public boolean delete(Long id) {
        if (employeeRepo.existsById(id)) {
            employeeRepo.deleteById(id);
            return true;
        } else {
            throw new RuntimeException("Employee doesnt exist");
        }
    }

    @Transactional
    public EmployeeResponseDTO uploadProfileImage(Long id, MultipartFile file) {
        try{
            Employee employee = employeeRepo.findById(id).orElse(null);
            if (employee==null) {
                throw new RuntimeException("Employee not found");
            }
            String file_name = fileUploadService.saveImage(file);
            employee.setProfile_image(file_name);
            employeeRepo.save(employee);
            return entityMapper.toEmployeeResponseDTO(employee);
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public ResponseEntity<Resource> getProfileImage(Long id, ServletWebRequest request) {
        try{
            Employee employee = employeeRepo.findById(id).orElse(null);
            if (employee==null) {
                throw new RuntimeException("Employee not found");
            }
            String file_path = employee.getProfile_image();
            Path imagePath = Paths.get(file_path);
            Resource imgFile = new UrlResource(imagePath.toUri());

            long lastModified = imgFile.lastModified();

            if (request.checkNotModified(lastModified)){
                return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
            }

            return ResponseEntity
                    .ok()
                    .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).cachePublic())
                    .lastModified(lastModified)
                    .body(imgFile);


        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

}
