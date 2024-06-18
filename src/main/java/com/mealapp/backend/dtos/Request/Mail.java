package com.mealapp.backend.dtos.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Mail {

    @Schema(description = "Email address to which the OTP will be sent")
    private String email;

    @Schema(description = "One-Time Password (OTP) to be sent")
    private String otp;
}
