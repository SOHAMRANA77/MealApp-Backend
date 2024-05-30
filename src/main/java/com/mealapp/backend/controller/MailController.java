package com.mealapp.backend.controller;

import com.mealapp.backend.dtos.Request.ChangePassword;
import com.mealapp.backend.dtos.Response.LogResponse;
import com.mealapp.backend.dtos.Request.Mail;
import com.mealapp.backend.repository.EmployeeRepo;
import com.mealapp.backend.service.EmployeeService;
import com.mealapp.backend.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mealApp/api")
@CrossOrigin(origins = "http://localhost:4200")
public class MailController {

    private final EmployeeRepo employeeRepo;
    private final OtpService otpService;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeService employeeService;

    @Autowired
    public MailController(EmployeeRepo employeeRepo, OtpService otpService, PasswordEncoder passwordEncoder, EmployeeService employeeService) {
        this.employeeRepo = employeeRepo;
        this.otpService = otpService;
        this.passwordEncoder = passwordEncoder;
        this.employeeService = employeeService;
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

    @PutMapping("/changePasswordByOtp")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassword changePasswordRequest) {
        return employeeService.changePasswordByOTP(changePasswordRequest);
    }
}
