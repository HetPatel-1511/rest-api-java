package com.example.restapi.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.util.Arrays;

@Component
@Order(1)
public class AuthFilter implements Filter {
    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (!httpRequest.getRequestURI().equals("/auth/login")){
            Cookie[] cookies = httpRequest.getCookies();
            String token = "";
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token")){
                        token=cookie.getValue();
                    }
                }
            }
            // TODO: Real JWT token
            if (!token.equals("REAL_JWT_TOKEN_HERE")){
                throw new AuthenticationException("Invalid token");
            }
        }
        chain.doFilter(request, response);
    }
}