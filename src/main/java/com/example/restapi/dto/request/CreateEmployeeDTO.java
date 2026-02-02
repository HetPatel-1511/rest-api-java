package com.example.restapi.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateEmployeeDTO {
    @NotNull
    @Size(max = 200)
    private String name;

    @NotNull
    @Size(max = 200)
    private String username;

    @NotNull
    @Size(min = 6, message = "Password should have minimum 6 characters")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
