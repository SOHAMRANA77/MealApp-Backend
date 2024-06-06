package com.mealapp.backend.service.Implementation;

import com.mealapp.backend.dtos.Request.Mail;
import com.mealapp.backend.dtos.Response.LogResponse;
import com.mealapp.backend.repository.EmployeeRepo;
import com.mealapp.backend.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements MailService {

    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private OtpImplementation otpImplementation;

    @Override
    public ResponseEntity<?> sendMail(Mail mail) {
        LogResponse mailResponse;

        if(employeeRepo.findFirstByEmail(mail.getEmail()).isPresent()){
            mailResponse = otpImplementation.sendOtp(mail.getEmail());
        }
        else {
            mailResponse = new LogResponse("Email not Exist", false);
        }
        return ResponseEntity.ok(mailResponse);
    }

    @Override
    public ResponseEntity<?> verifyOtp(Mail mail) {
        LogResponse verifyOtpRes;
        if(employeeRepo.findFirstByEmail(mail.getEmail()).isPresent()){
            verifyOtpRes = otpImplementation.verifyOtp(mail.getEmail(),mail.getOtp());
        }
        else {
            verifyOtpRes = new LogResponse("Email not Exist", false);
        }
        return ResponseEntity.ok(verifyOtpRes);
    }
}
