package com.mealapp.backend.dtos.Request;

import com.mealapp.backend.enums.UserRole;
import lombok.Data;

@Data
public class RegisterEmployee {

    private String name;
    private String email;
    private String currentPassword;
    private String department;
    private String phoneNo;
    private UserRole userRole;

    public RegisterEmployee(String name, String email, String currentPassword, String department, String phoneNo, UserRole userRole) {
        this.name = name;
        this.email = email;
        this.currentPassword = currentPassword;
        this.department = department;
        this.phoneNo = phoneNo;
        this.userRole = userRole;
    }

    public RegisterEmployee() {
    }
}
