package com.mealapp.backend.controller;

import com.mealapp.backend.dtos.Request.ChangePassword;
import com.mealapp.backend.dtos.Response.LogResponse;
import com.mealapp.backend.dtos.Request.Mail;
import com.mealapp.backend.repository.EmployeeRepo;
import com.mealapp.backend.service.EmployeeService;
import com.mealapp.backend.service.Implementation.OtpImplementation;
import com.mealapp.backend.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mealApp/api")
@CrossOrigin(origins = "http://localhost:4200")
public class MailController {
    private final EmployeeService employeeService;
    private final MailService mailService;

    public MailController(EmployeeService employeeService, MailService mailService) {
        this.employeeService = employeeService;
        this.mailService = mailService;
    }


    @PostMapping("/sendMail")
    public ResponseEntity<?> sendMail(@RequestBody Mail mail){
        return mailService.sendMail(mail);
    }

    @PostMapping("/verifyOtp")
    public ResponseEntity<?> verifyOtp(@RequestBody Mail mail){
        return mailService.verifyOtp(mail);
    }

    @PutMapping("/changePasswordByOtp")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassword changePasswordRequest) {
        return employeeService.changePasswordByOTP(changePasswordRequest);
    }
}
