package com.example.restapi.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/versioning")
public class VersioningController {

    @GetMapping("/v2/employee")
    public String getVersionURI() {
        return "URL Path Versioning";
    }

    @GetMapping(value = "/employee/param", params = "version=2")
    public String getVersionParam() {
        return "Query Parameter Versioning";
    }

    @GetMapping(value = "/employee/header", headers = "X-API-VERSION=2")
    public String getVersionHeader() {
        return "Custom Header Versioning";
    }

    @GetMapping(value = "/employee/produces", produces = "application/vnd.company.app-v2+json")
    public String getVersionProduces() {
        return "Content Negotiation Versioning";
    }
}