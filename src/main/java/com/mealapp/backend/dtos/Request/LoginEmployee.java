package com.mealapp.backend.dtos.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginEmployee {

    @Schema(description = "Email address of the employee", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Current password for the employee account", example = "password")
    private String currentPassword;
}
