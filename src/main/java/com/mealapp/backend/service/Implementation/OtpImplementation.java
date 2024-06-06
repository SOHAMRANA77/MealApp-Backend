package com.mealapp.backend.service.Implementation;

import com.mealapp.backend.dtos.Response.LogResponse;
import com.mealapp.backend.entities.Otp;
import com.mealapp.backend.repository.OtpRepo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
@Service
public class OtpImplementation {

    private final MailSenderService mailService;
    private final Random random;
    private final OtpRepo otpRepo;

    public OtpImplementation(MailSenderService mailService, Random random, OtpRepo otpRepo) {
        this.mailService = mailService;
        this.random = random;
        this.otpRepo = otpRepo;
    }

    public String generateOtp() {
        return String.format("%06d", random.nextInt(999999));
    }

    public LogResponse sendOtp(String email){
        Optional<Otp> otpEntity = otpRepo.findByEmail(email);
        String otp;
        if(!otpEntity.isPresent()){
            otp = generateOtp();
            Otp newOtp = new Otp(email, otp);
            otpRepo.save(newOtp);
        } else {
            otp = otpEntity.get().getOtp();
        }

        String subject = "Your OTP for Meal App";
        String message = "Hello User,\n\nYour One-Time Password (OTP) for Meal App is: " + otp + "\n\nThis OTP is valid for 10 minutes.\n\nThank you,\nMeal App Team";
        mailService.sendNewMail(email, subject, message);
        return new LogResponse("OTP Sent", true);
    }

    public LogResponse verifyOtp(String email, String otp) {
        Optional<Otp> otpEntity = otpRepo.findByEmailAndOtp(email, otp);
        if(otpEntity.isPresent()){
            Long id = otpEntity.get().getId();
            Optional<Otp> o = otpRepo.findById(id);
            o.get().setUsed(true);
            otpRepo.save(o.get());
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
