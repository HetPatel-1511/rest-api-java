package com.example.restapi.controllers;

import com.example.restapi.dto.Create;
import com.example.restapi.dto.Update;
import com.example.restapi.dto.request.EmployeeDTO;
import com.example.restapi.dto.request.UpdateEmployeeDTO;
import com.example.restapi.dto.response.EmployeeResponseDTO;
import com.example.restapi.entities.Employee;
import com.example.restapi.repos.EmployeeRepo;
import com.example.restapi.services.EmployeeService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.core.io.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeRepo employeeRepo) {
        this.employeeService = employeeService;
        this.employeeRepo = employeeRepo;
    }

    @GetMapping
    public List<EmployeeResponseDTO> getAll(){
        return employeeService.getAll();
    }

    @PostMapping
    public EmployeeResponseDTO create(@RequestBody @Validated(Create.class) EmployeeDTO dto){
        return employeeService.create(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getByID(@PathVariable Long id){
        EmployeeResponseDTO dto = employeeService.getByID(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            throw new RuntimeException("Employee with id "+id+" not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> update(@PathVariable Long id, @RequestBody @Validated(Update.class) EmployeeDTO dto){
        EmployeeResponseDTO resDTO = employeeService.update(id, dto);
        if (resDTO != null) {
            return ResponseEntity.ok(resDTO);
        } else {
            throw new RuntimeException("Something went wrong.");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateIsActive(@PathVariable Long id, @RequestParam("is_active") String is_active){
        if (
                is_active.equals("true") || is_active.equals("false") ||
                is_active.equals("True") || is_active.equals("False") ||
                is_active.equals("1") || is_active.equals("0")
        ) {
            EmployeeResponseDTO resDTO = employeeService.updateIsActive(id, is_active);
            if (resDTO != null) {
                return ResponseEntity.ok(resDTO);
            } else {
                throw new RuntimeException("Something went wrong.");
            }
        } else {
            throw new RuntimeException("Invalid value passed in is_active.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> updateIsActive(@PathVariable Long id){
        boolean res = employeeService.delete(id);
        return ResponseEntity.ok(res);
    }

    @PatchMapping("/{id}/profile-image")
    public ResponseEntity<EmployeeResponseDTO> uploadProfilePicture(@PathVariable Long id, @RequestParam("file") MultipartFile file){
        EmployeeResponseDTO res = employeeService.uploadProfileImage(id, file);
        return ResponseEntity.ok(res);
    }

    @Hidden
    @GetMapping("/{id}/profile-image")
    public ResponseEntity<Resource> getProfilePicture(@PathVariable Long id, ServletWebRequest request) throws IOException {

        Employee employee = employeeRepo.findById(id).orElse(null);
        if (employee == null) {
            throw new RuntimeException("Employee not found");
        }
        String file_path = employee.getProfile_image();
        Path imagePath = Paths.get(file_path).normalize();
        Resource imgFile = new UrlResource(imagePath.toUri());

        long lastModified = imgFile.lastModified();

        if (request.checkNotModified(lastModified)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).cachePublic())
                .lastModified(lastModified)
                .body(imgFile);


    }
}
