package com.example.restapi.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class UploadProfileImageDTO {

    public MultipartFile file;
}
