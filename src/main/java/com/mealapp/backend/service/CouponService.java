package com.mealapp.backend.service;

import com.mealapp.backend.dtos.Request.DeleteCoupon;
import com.mealapp.backend.dtos.Response.Booked_Response;
import com.mealapp.backend.dtos.Response.LogResponse;
import com.mealapp.backend.enums.MenuType;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface CouponService {
    List<Booked_Response> getCouponByID(Long empId);

    LogResponse DeleteCouponByDateAndMealTypeAndEmpID(DeleteCoupon deleteCoupon);

    ResponseEntity<?> getQRcdoe(Long empId, LocalDate date, MenuType type);
}
