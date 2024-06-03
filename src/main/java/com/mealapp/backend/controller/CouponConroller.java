package com.mealapp.backend.controller;


import com.mealapp.backend.dtos.Request.DeleteCoupon;
import com.mealapp.backend.dtos.Response.Booked_Response;
import com.mealapp.backend.dtos.Response.BookingsResponse;
import com.mealapp.backend.dtos.Response.LogResponse;
import com.mealapp.backend.entities.Coupon;
import com.mealapp.backend.enums.MenuType;
import com.mealapp.backend.service.CouponService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("mealApp/api")
@CrossOrigin(origins = "http://localhost:4200")
public class CouponConroller {

    @Autowired
    private CouponService couponService;

    @GetMapping("/getCouponByID{emp_id}")
    public List<Booked_Response> getCoupon(@RequestParam Long emp_id){
        return couponService.getCouponByID(emp_id);
    }

    @PutMapping("/DeleteCoupon")
    public ResponseEntity<?> DeleteCoupon(@RequestBody DeleteCoupon deleteCoupon){
        LogResponse isCancel = couponService.DeleteCouponByDateAndMealTypeAndEmpID(deleteCoupon);
        return ResponseEntity.ok(isCancel);
    }


//    @GetMapping("/getBookingsByEmpId")
//    public BookingsResponse getBookings(@RequestParam Long emp_id) {
//        List<Booked_Response> bookedResponses = couponService.getCouponByID(emp_id);
//
//        Map<String, BookingsResponse.MealType> bookingsMap = new HashMap<>();
//
//        for (Booked_Response response : bookedResponses) {
//            String date = response.getDate().toString();
//            BookingsResponse.MealType mealType = bookingsMap.getOrDefault(date, new BookingsResponse.MealType());
//
//            if (response.getType() == MenuType.LUNCH) {
//                mealType.setLUNCH(true);
//            } else if (response.getType() == MenuType.DINNER) {
//                mealType.setDINNER(true);
//            }
//
//            bookingsMap.put(date, mealType);
//        }
//
//        BookingsResponse bookingsResponse = new BookingsResponse();
//        bookingsResponse.setBookings(bookingsMap);
//
//        return bookingsResponse;
//    }
}
