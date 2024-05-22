package com.mealapp.backend.service;

import com.mealapp.backend.dtos.Response.LogResponse;
import com.mealapp.backend.entities.Otp;
import com.mealapp.backend.repository.OtpRepo;
import com.mealapp.backend.service.Implementation.MailSenderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpService {

    private final MailSenderService mailService;
    private final Random random;
    private final OtpRepo otpRepo;

    public OtpService(MailSenderService mailService, Random random, OtpRepo otpRepo) {
        this.mailService = mailService;
        this.random = random;
        this.otpRepo = otpRepo;
    }

    public String generateOtp() {
        return String.format("%06d", random.nextInt(999999));
    }

    public LogResponse sendOtp(String email){
        String otp = generateOtp();
        Otp otpStore = new Otp(email,otp);
        otpRepo.save(otpStore);
        if(otpRepo.findByEmail(email).isPresent()){
            String subject = "Your OTP for Meal App";
            String message = "Hello User,\n\nYour One-Time Password (OTP) for Meal App is: " + otp + "\n\nThis OTP is valid for 10 minutes.\n\nThank you,\nMeal App Team";
            mailService.sendNewMail(email, subject, message);
            return new LogResponse("OTP Sent", true);
        }
        return new LogResponse("OTP Failed", false);
    }

    public LogResponse verifyOtp(String email, String otp) {
        if(otpRepo.findByEmailAndOtp(email,otp).isPresent()){
            return new LogResponse("OTP Accepted", true);
        }
        return new LogResponse("Email or Otp is Wrong, Try to reSend it", false);
    }

    @Scheduled(fixedRate = 60000) // Run every minute
    @Transactional
    public void deleteExpiredOtps() {
        LocalDateTime expirationTime = LocalDateTime.now().minusMinutes(3);
        otpRepo.deleteByCreatedAtBefore(expirationTime);
    }
}
