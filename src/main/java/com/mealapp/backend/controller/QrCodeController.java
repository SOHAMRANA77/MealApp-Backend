package com.mealapp.backend.controller;

import com.mealapp.backend.dtos.Response.CouponResponse;
import com.mealapp.backend.dtos.Response.LogResponse;
import com.mealapp.backend.enums.MenuType;
import com.mealapp.backend.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("mealApp/api")
@CrossOrigin(origins = "http://localhost:4200")
public class QrCodeController {

    @Autowired
    private CouponService couponService;

    @Operation(summary = "Get QR code",
            responses = {
                    @ApiResponse(description = "Successful Response",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = CouponResponse.class))),
                    @ApiResponse(description = "Coupon not found",
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = LogResponse.class)))
            })
    @GetMapping("/GetQrCode")
    public ResponseEntity<?> getQrCode(
            @Parameter(description = "Employee ID", example = "123")
            @RequestParam("emp_id") Long emp_id,

            @Parameter(description = "Date in ISO format (YYYY-MM-DD)", example = "2024-06-18")
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,

            @Parameter(description = "Menu type")
            @RequestParam("type") MenuType type) {

        System.out.println("in Qrcode\n\n\n\n\n");
        return couponService.getQRcdoe(emp_id, date, type);
    }
}
