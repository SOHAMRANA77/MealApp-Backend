package com.mealapp.backend.controller;

import com.mealapp.backend.dtos.Request.ChangePassword;
import com.mealapp.backend.dtos.Request.LoginEmployee;
import com.mealapp.backend.dtos.Request.RegisterEmployee;
import com.mealapp.backend.dtos.Response.LogResponse;
import com.mealapp.backend.entities.Department;
import com.mealapp.backend.service.EmployeeService;
import com.mealapp.backend.service.departmentService;

import jakarta.servlet.http.HttpServletResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    public EmployeeController(EmployeeService employeeService, departmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @Operation(summary = "Get employee name by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Name retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = @Content)
    })
    @GetMapping("/getName")
    public String getName(@RequestParam("id") Long id) {
        return employeeService.getNameById(id);
    }

    @Operation(summary = "Create a new department")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Department created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Department.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    })
    @PostMapping("/addDepart")
    public Department createDepartment(@RequestBody Department department) {
        return departmentService.createDepartment(department);
    }

    @Operation(summary = "Get list of all departments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Departments retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Department.class)) })
    })
    @GetMapping("/ListDepart")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @Operation(summary = "Register a new employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee registered successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LogResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    })
    @PostMapping("/Register")
    public ResponseEntity<?> registerEmployee(@RequestBody RegisterEmployee registerEmployee) {
        LogResponse registerMessage = employeeService.registerEmployee(registerEmployee);
        return ResponseEntity.ok(registerMessage);
    }

    @Operation(summary = "Employee login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseEntity.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)
    })
    @PostMapping("/Login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginEmployee loginEmployee, HttpServletResponse response) throws IOException {
        return employeeService.Login(loginEmployee, response);
    }

    @Operation(summary = "Change employee password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password changed successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LogResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = @Content)
    })
    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassword changePassword) {
        return employeeService.changePassword(changePassword);
    }
}
