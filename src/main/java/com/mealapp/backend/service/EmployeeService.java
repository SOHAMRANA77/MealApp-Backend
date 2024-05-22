package com.mealapp.backend.service;

import com.mealapp.backend.dtos.Request.RegisterEmployee;
import com.mealapp.backend.dtos.Response.LogResponse;
import com.mealapp.backend.entities.Employee;
import com.mealapp.backend.enums.UserRole;
import org.springframework.context.annotation.Bean;

public interface EmployeeService {
    @Bean
    LogResponse registerEmployee(RegisterEmployee registerEmployee);

//    Employee findByUserRole(UserRole userRole);


}
