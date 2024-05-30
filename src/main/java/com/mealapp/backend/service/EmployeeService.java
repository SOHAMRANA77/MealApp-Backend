package com.mealapp.backend.service;

import com.mealapp.backend.dtos.Request.ChangePassword;
import com.mealapp.backend.dtos.Request.LoginEmployee;
import com.mealapp.backend.dtos.Request.RegisterEmployee;
import com.mealapp.backend.dtos.Response.LogResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface EmployeeService {
    @Bean
    LogResponse registerEmployee(RegisterEmployee registerEmployee);

    ResponseEntity<?> Login(LoginEmployee loginEmployee, HttpServletResponse response) throws IOException;

//    Employee findByUserRole(UserRole userRole);

    ResponseEntity<?> changePasswordByOTP(ChangePassword changePasswordRequest);


    ResponseEntity<?> changePassword(ChangePassword changePassword);
}
