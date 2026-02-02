package com.example.restapi.controllers;

import com.example.restapi.dto.request.LoginEmployeeDTO;
import com.example.restapi.dto.response.AuthEmployeeResponseDTO;
import com.example.restapi.services.AuthService;
import com.example.restapi.services.EmployeeService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthEmployeeResponseDTO login(@Valid @RequestBody LoginEmployeeDTO dto, HttpServletResponse response){
        AuthEmployeeResponseDTO resDTO = authService.login(dto);
        Cookie cookie = new Cookie("token", resDTO.getToken());
        cookie.setMaxAge(3600);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return resDTO;
    }
}
