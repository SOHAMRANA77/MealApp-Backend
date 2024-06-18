package com.mealapp.backend.controller;

import com.mealapp.backend.dtos.Request.ChangePassword;
import com.mealapp.backend.dtos.Request.Mail;
import com.mealapp.backend.dtos.Response.LogResponse;
import com.mealapp.backend.service.EmployeeService;
import com.mealapp.backend.service.MailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Operation(summary = "Send an email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email sent successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseEntity.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid email details",
                    content = @Content)
    })
    @PostMapping("/sendMail")
    public ResponseEntity<?> sendMail(@RequestBody Mail mail) {
        return mailService.sendMail(mail);
    }

    @Operation(summary = "Verify OTP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OTP verified successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseEntity.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid OTP",
                    content = @Content)
    })
    @PostMapping("/verifyOtp")
    public ResponseEntity<?> verifyOtp(@RequestBody Mail mail) {
        return mailService.verifyOtp(mail);
    }

    @Operation(summary = "Change password using OTP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password changed successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LogResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    @PutMapping("/changePasswordByOtp")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassword changePasswordRequest) {
        return employeeService.changePasswordByOTP(changePasswordRequest);
    }
}
