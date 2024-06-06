package com.mealapp.backend.service;

import com.mealapp.backend.dtos.Request.Mail;
import org.springframework.http.ResponseEntity;

public interface MailService {
    ResponseEntity<?> sendMail(Mail mail);

    ResponseEntity<?> verifyOtp(Mail mail);
}
