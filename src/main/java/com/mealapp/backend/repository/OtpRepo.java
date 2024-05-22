package com.mealapp.backend.repository;


import com.mealapp.backend.entities.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OtpRepo extends JpaRepository<Otp, Long> {
    Optional<Otp> findByEmail(String email);
    Optional<Otp> findByEmailAndOtp(String email,String otp);
    void deleteByCreatedAtBefore(LocalDateTime expirationTime);
}
