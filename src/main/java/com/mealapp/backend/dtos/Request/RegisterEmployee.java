package com.mealapp.backend.dtos.Request;

import com.mealapp.backend.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RegisterEmployee {

    @Schema(description = "Name of the employee", example = "John Doe")
    private String name;

    @Schema(description = "Email address of the employee", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Current password for the employee account", example = "password")
    private String currentPassword;

    @Schema(description = "Department where the employee belongs", example = "Finance")
    private String department;

    @Schema(description = "Phone number of the employee", example = "+1234567890")
    private String phoneNo;

    @Schema(description = "Role of the employee", example = "EMPLOYEE")
    private UserRole userRole;

    @Schema(description = "Location of the employee", example = "New York")
    private String location;

    public RegisterEmployee(String name, String email, String currentPassword, String department, String phoneNo, UserRole userRole, String location) {
        this.name = name;
        this.email = email;
        this.currentPassword = currentPassword;
        this.department = department;
        this.phoneNo = phoneNo;
        this.userRole = userRole;
        this.location = location;
    }

    public RegisterEmployee() {
    }
}
