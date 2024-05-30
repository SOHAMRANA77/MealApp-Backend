package com.mealapp.backend.controller;


import com.mealapp.backend.dtos.Request.ChangePassword;
import com.mealapp.backend.dtos.Request.LoginEmployee;
import com.mealapp.backend.dtos.Request.RegisterEmployee;
import com.mealapp.backend.dtos.Response.LogResponse;
import com.mealapp.backend.entities.Department;
import com.mealapp.backend.service.EmployeeService;
import com.mealapp.backend.service.departmentService;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("mealApp/api")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final departmentService departmentService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, com.mealapp.backend.service.departmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @PostMapping("/addDepart")
    public Department createDepartment(@RequestBody Department department) {
        return departmentService.createDepartment(department);
    }
    @GetMapping("/ListDepart")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }


    @PostMapping("/Register")
    public ResponseEntity<?> registerEmployee(@RequestBody RegisterEmployee registerEmployee){
        LogResponse RegisterMassage = employeeService.registerEmployee(registerEmployee);
        return ResponseEntity.ok(RegisterMassage);
    }

    @PostMapping("/Login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginEmployee loginEmployee, HttpServletResponse response) throws IOException {
        return employeeService.Login(loginEmployee,response);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> chanegePassword(@RequestBody ChangePassword changePassword){
        System.out.println(changePassword.toString());
        return employeeService.changePassword(changePassword);
    }



}
