package com.mealapp.backend.service.Implementation.UUID;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class CouponCodeGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6; // Adjust length if needed
    private static final SecureRandom random = new SecureRandom();

    public String generateUniqueCouponCode() {
        StringBuilder couponCode = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            couponCode.append(CHARACTERS.charAt(index));
        }
        return couponCode.toString();
    }
}
