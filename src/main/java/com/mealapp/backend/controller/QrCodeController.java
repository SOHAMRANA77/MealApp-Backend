package com.mealapp.backend.controller;

import com.mealapp.backend.enums.MenuType;
import com.mealapp.backend.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("mealApp/api")
@CrossOrigin(origins = "http://localhost:4200")
public class QrCodeController {

    @Autowired
    private CouponService couponService;

    @PostMapping("/GetQrCode")
    public String getQrCode(@RequestParam("emp_id") Long emp_id,
                            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                            @RequestParam("type") MenuType type) {
        return couponService.getQRcdoe(emp_id, date, type);

    }
}
