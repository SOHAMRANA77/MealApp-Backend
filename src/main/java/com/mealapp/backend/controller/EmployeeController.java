package com.mealapp.backend.controller;


import com.mealapp.backend.dtos.Request.LoginEmployee;
import com.mealapp.backend.dtos.Request.RegisterEmployee;
import com.mealapp.backend.dtos.Response.JWT_Response;
import com.mealapp.backend.dtos.Response.LogResponse;
import com.mealapp.backend.entities.Employee;
import com.mealapp.backend.repository.EmployeeRepo;
import com.mealapp.backend.service.EmployeeService;
import com.mealapp.backend.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("mealApp/api")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeController(EmployeeService employeeService, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtUtil, EmployeeRepo employeeRepo) {
        this.employeeService = employeeService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.employeeRepo = employeeRepo;
    }


    @PostMapping("/Register")
    public ResponseEntity<?> registerEmployee(@RequestBody RegisterEmployee registerEmployee){
        LogResponse RegisterMassage = employeeService.registerEmployee(registerEmployee);
        return ResponseEntity.ok(RegisterMassage);
    }

    @PostMapping("/Login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginEmployee loginEmployee, HttpServletResponse response) throws IOException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginEmployee.getEmail(), loginEmployee.getCurrentPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.ok(new JWT_Response("Incorrect Username or Password",false));
//            throw new BadCredentialsException("Incorrect Username or Password");

        } catch (DisabledException disabledException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User Not Active");
            return ResponseEntity.ok(new JWT_Response("User Not Active",false));
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginEmployee.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        Optional<Employee> optionalEmployee = employeeRepo.findFirstByEmail(userDetails.getUsername());
        JWT_Response loginResponse = new JWT_Response();
        if(optionalEmployee.isPresent()){
            loginResponse.setJwt(jwt);
            loginResponse.setId(optionalEmployee.get().getId());
            loginResponse.setUserRole(optionalEmployee.get().getUserRole());
            loginResponse.setStatus(true);
        }
        return ResponseEntity.ok(loginResponse);
    }


}
