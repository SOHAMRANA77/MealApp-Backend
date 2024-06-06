package com.mealapp.backend.service.Implementation;

import com.mealapp.backend.dtos.Request.ChangePassword;
import com.mealapp.backend.dtos.Request.LoginEmployee;
import com.mealapp.backend.dtos.Request.RegisterEmployee;
import com.mealapp.backend.dtos.Response.LogResponse;
import com.mealapp.backend.dtos.Response.Token_Response;
import com.mealapp.backend.entities.Department;
import com.mealapp.backend.entities.Employee;
import com.mealapp.backend.enums.NotificationType;
import com.mealapp.backend.enums.UserRole;
import com.mealapp.backend.repository.DepartmentRepo;
import com.mealapp.backend.repository.EmployeeRepo;
import com.mealapp.backend.service.EmployeeService;
import com.mealapp.backend.service.NotificationService;
import com.mealapp.backend.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class Employee_Implement implements EmployeeService {

    @Autowired
    private final EmployeeRepo employeeRepo;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final DepartmentRepo departmentRepository;

    @Autowired
    private final NotificationService notificationService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public Employee_Implement(EmployeeRepo employeeRepo, PasswordEncoder passwordEncoder, DepartmentRepo departmentRepository, NotificationService notificationService, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.employeeRepo = employeeRepo;
        this.passwordEncoder = passwordEncoder;
        this.departmentRepository = departmentRepository;
        this.notificationService = notificationService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }


    @PostConstruct
    public void createAdmin(){
        Employee admin = employeeRepo.findByUserRole(UserRole.ADMIN);
        if(admin == null){
            List<Department> departments = departmentRepository.findByName("JAVA");
            Department department = new Department();
            if (departments.isEmpty()) {
                department = new Department("JAVA","Vadodara");
                department = departmentRepository.save(department);
            } else if (departments.size() == 1) {
                department = departments.get(0);
            }
            Employee user = new Employee("admin","admin@admin.com","admin",department,"7069494809",UserRole.ADMIN);
            employeeRepo.save(user);
        }
    }

    @Override
    public LogResponse registerEmployee(RegisterEmployee registerEmployee) {
        try{
            List<Department> departments = departmentRepository.findByNameAndLocation(registerEmployee.getDepartment(),registerEmployee.getLocation());
            Department department;

            if (departments.isEmpty()) {
                return new LogResponse("Department not found", HttpStatus.BAD_REQUEST, false);
            } else if (departments.size() == 1) {
                department = departments.get(0);
            } else {
                // Handle the case where multiple departments with the same name exist
                return new LogResponse("Multiple departments found with name: "+registerEmployee.getDepartment(), HttpStatus.BAD_REQUEST, false);
            }

            Employee employee = new Employee(registerEmployee.getName(),
                    registerEmployee.getEmail(),
                    this.passwordEncoder.encode(registerEmployee.getCurrentPassword()),
//                                        employeeDto.getCurrentPassword(),
                    department,
                    registerEmployee.getPhoneNo(),
                    registerEmployee.getUserRole());

            employeeRepo.save(employee);
            notificationService.AddNotification(employee,"Welcome to Meal App", NotificationType.WELCOME);
            return new LogResponse("Registration Success", HttpStatus.CREATED, true);

        }catch (Exception e){
            return new LogResponse("Registration Failed", HttpStatus.BAD_REQUEST, false);
        }
    }

    @Override
    public ResponseEntity<?> Login(LoginEmployee loginEmployee, HttpServletResponse response) throws IOException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginEmployee.getEmail(), loginEmployee.getCurrentPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.ok(new Token_Response("Incorrect Username or Password", HttpStatus.BAD_REQUEST,false));
//            throw new BadCredentialsException("Incorrect Username or Password");

        } catch (DisabledException disabledException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User Not Active");
            return ResponseEntity.ok(new Token_Response("User Not Active",HttpStatus.BAD_REQUEST,false));
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginEmployee.getEmail());
//        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
//        Optional<Employee> optionalEmployee = employeeRepo.findFirstByEmail(userDetails.getUsername());
//        JWT_Response loginResponse = new JWT_Response();
//        if(optionalEmployee.isPresent()){
//            loginResponse.setJwt(jwt);
//            loginResponse.setId(optionalEmployee.get().getId());
//            loginResponse.setUserRole(optionalEmployee.get().getUserRole());
//            loginResponse.setStatus(true);
//        }
//        return ResponseEntity.ok(loginResponse);
        Optional<Employee> optionalEmployee = employeeRepo.findFirstByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername(),optionalEmployee.get().getId(),optionalEmployee.get().getUserRole());
        return ResponseEntity.ok(new Token_Response("Login Successfully",HttpStatus.CREATED,true,jwt));
    }

    @Override
    public ResponseEntity<?> changePasswordByOTP(ChangePassword changePasswordRequest) {
        String email = changePasswordRequest.getEmail();
        String newPassword = changePasswordRequest.getNewPassword();

        // Validate input
        if (email == null || email.isEmpty() || newPassword == null || newPassword.isEmpty()) {
            return ResponseEntity.ok(new LogResponse("Email and password must not be empty",HttpStatus.BAD_REQUEST,false));
        }

        // Fetch the employee by email
        Optional<Employee> optionalEmployee = employeeRepo.findFirstByEmail(email);
        if (!optionalEmployee.isPresent()) {
            return ResponseEntity.ok(new LogResponse("Employee not found", HttpStatus.NOT_FOUND, false));
        }

        Employee employee = optionalEmployee.get();

        // Check if the new password is different from the current and previous passwords
        if (passwordEncoder.matches(newPassword, employee.getCurrentPassword()) ||
                passwordEncoder.matches(newPassword, employee.getOldPassword1()) ||
                passwordEncoder.matches(newPassword, employee.getOldPassword2()) ||
                passwordEncoder.matches(newPassword, employee.getOldPassword3()) ||
                passwordEncoder.matches(newPassword, employee.getOldPassword4())) {
            return ResponseEntity.ok(new LogResponse("New password must be different from the current and previous passwords", HttpStatus.BAD_REQUEST,false));
        }

        // Rotate passwords
        employee.setOldPassword4(employee.getOldPassword3());
        employee.setOldPassword3(employee.getOldPassword2());
        employee.setOldPassword2(employee.getOldPassword1());
        employee.setOldPassword1(employee.getCurrentPassword());
        employee.setCurrentPassword(passwordEncoder.encode(newPassword));

        // Save the updated employee back to the repository
        employeeRepo.save(employee);
        notificationService.AddNotification(employee,"Change Password successfully by OTP",NotificationType.OTP);
        return ResponseEntity.ok(new LogResponse("Password changed successfully", HttpStatus.OK, true));
    }

    @Override
    public ResponseEntity<?> changePassword(ChangePassword changePassword) {
        Employee employee = employeeRepo.findByEmail(changePassword.getEmail());
        if(!passwordEncoder.matches(changePassword.getOldPassword(), employee.getCurrentPassword())){
            return  ResponseEntity.ok(new LogResponse("Password or Email is incorrect",HttpStatus.BAD_REQUEST, false));
        }
        String newPassword = changePassword.getNewPassword();

        if (passwordEncoder.matches(newPassword, employee.getCurrentPassword()) ||
                passwordEncoder.matches(newPassword, employee.getOldPassword1()) ||
                passwordEncoder.matches(newPassword, employee.getOldPassword2()) ||
                passwordEncoder.matches(newPassword, employee.getOldPassword3()) ||
                passwordEncoder.matches(newPassword, employee.getOldPassword4())) {
            return  ResponseEntity.ok(new LogResponse("New password must be different from the current and previous passwords",HttpStatus.BAD_REQUEST, false));

        }
        employee.setOldPassword4(employee.getOldPassword3());
        employee.setOldPassword3(employee.getOldPassword2());
        employee.setOldPassword2(employee.getOldPassword1());
        employee.setOldPassword1(employee.getCurrentPassword());
        employee.setCurrentPassword(passwordEncoder.encode(newPassword));

        employeeRepo.save(employee);
        notificationService.AddNotification(employee,"Change Password successfully",NotificationType.PASSWORD);
        return  ResponseEntity.ok(new LogResponse("Password changed successfully",HttpStatus.ACCEPTED, true));

    }

    public String getNameById(Long id) {
        String fullName = employeeRepo.findEmpNameById(id);
        return extractFirstName(fullName);
    }

    private String extractFirstName(String fullName) {
        if (fullName != null && !fullName.isEmpty()) {
            String[] nameParts = fullName.split(" ");
            return nameParts[0]; // Return the first part of the name
        }
        return "";
    }

    public String getFullName(Long id){
        return employeeRepo.findEmpNameById(id);
    }


}
