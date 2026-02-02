package com.example.restapi.controllers;

import com.example.restapi.dto.request.LoginEmployeeDTO;
import com.example.restapi.dto.response.AuthEmployeeResponseDTO;
import com.example.restapi.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/auth")
public class AuthControllerV2 {

    @PostMapping("/login")
    public String login(){

        return "This is the v2 login API";
    }
}
