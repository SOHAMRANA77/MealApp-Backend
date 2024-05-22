package com.mealapp.backend.controller;

import com.mealapp.backend.dtos.Request.ChangePassword;
import com.mealapp.backend.dtos.Response.LogResponse;
import com.mealapp.backend.dtos.Request.Mail;
import com.mealapp.backend.entities.Employee;
import com.mealapp.backend.repository.EmployeeRepo;
import com.mealapp.backend.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("mealApp/api")
@CrossOrigin(origins = "http://localhost:4200")
public class MailController {

    private final EmployeeRepo employeeRepo;
    private final OtpService otpService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MailController(EmployeeRepo employeeRepo, OtpService otpService, PasswordEncoder passwordEncoder) {
        this.employeeRepo = employeeRepo;
        this.otpService = otpService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/sendMail")
    public ResponseEntity<?> sendMail(@RequestBody Mail mail){
        LogResponse mailResponse;

        if(employeeRepo.findFirstByEmail(mail.getEmail()).isPresent()){
            mailResponse = otpService.sendOtp(mail.getEmail());
        }
        else {
            mailResponse = new LogResponse("Email not Exist", false);
        }
        return ResponseEntity.ok(mailResponse);
    }

    @PostMapping("/verifyOtp")
    public ResponseEntity<?> verifyOtp(@RequestBody Mail mail){
        LogResponse verifyOtpRes;
        if(employeeRepo.findFirstByEmail(mail.getEmail()).isPresent()){
            verifyOtpRes = otpService.verifyOtp(mail.getEmail(),mail.getOtp());
        }
        else {
            verifyOtpRes = new LogResponse("Email not Exist", false);
        }
        return ResponseEntity.ok(verifyOtpRes);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassword changePasswordRequest) {
        String email = changePasswordRequest.getEmail();
        String newPassword = changePasswordRequest.getNewPassword();

        // Validate input
        if (email == null || email.isEmpty() || newPassword == null || newPassword.isEmpty()) {
            return new ResponseEntity<>("Email and password must not be empty", HttpStatus.BAD_REQUEST);
        }

        // Fetch the employee by email
        Optional<Employee> optionalEmployee = employeeRepo.findFirstByEmail(email);
        if (!optionalEmployee.isPresent()) {
            return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
        }

        Employee employee = optionalEmployee.get();

        // Check if the new password is different from the current and previous passwords
        if (passwordEncoder.matches(newPassword, employee.getCurrentPassword()) ||
                passwordEncoder.matches(newPassword, employee.getOldPassword1()) ||
                passwordEncoder.matches(newPassword, employee.getOldPassword2()) ||
                passwordEncoder.matches(newPassword, employee.getOldPassword3()) ||
                passwordEncoder.matches(newPassword, employee.getOldPassword4())) {
            return new ResponseEntity<>("New password must be different from the current and previous passwords", HttpStatus.BAD_REQUEST);
        }

        // Rotate passwords
        employee.setOldPassword4(employee.getOldPassword3());
        employee.setOldPassword3(employee.getOldPassword2());
        employee.setOldPassword2(employee.getOldPassword1());
        employee.setOldPassword1(employee.getCurrentPassword());
        employee.setCurrentPassword(passwordEncoder.encode(newPassword));

        // Save the updated employee back to the repository
        employeeRepo.save(employee);

        return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
    }
}
