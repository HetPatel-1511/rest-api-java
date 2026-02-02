package com.example.restapi.services;

import com.example.restapi.dto.request.LoginEmployeeDTO;
import com.example.restapi.dto.response.AuthEmployeeResponseDTO;
import com.example.restapi.entities.Employee;
import com.example.restapi.mapper.EntityMapper;
import com.example.restapi.repos.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final EmployeeRepo employeeRepo;
    private final EntityMapper entityMapper;

    @Autowired
    public AuthService(EmployeeRepo employeeRepo, EntityMapper entityMapper){
        this.employeeRepo = employeeRepo;
        this.entityMapper = entityMapper;
    }

    public AuthEmployeeResponseDTO login(LoginEmployeeDTO dto) {
        Employee employee = employeeRepo.findByUsername(dto.getUsername());
        if (employee==null) {
            throw new RuntimeException("Employee not found");
        }

        // TODO: password hashing and implementing real JWT
        if (!employee.getPassword().equals(dto.getPassword())){
            throw new RuntimeException("Incorrect Password");
        }

        AuthEmployeeResponseDTO authEmployeeResponseDTO = entityMapper.toAuthEmployeeResponseDTO(employee);
        authEmployeeResponseDTO.setToken("REAL_JWT_TOKEN_HERE");

        return authEmployeeResponseDTO;

    }
}
