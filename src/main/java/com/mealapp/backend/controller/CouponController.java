package com.mealapp.backend.controller;

import com.mealapp.backend.dtos.Request.DeleteCoupon;
import com.mealapp.backend.dtos.Response.Booked_Response;
import com.mealapp.backend.dtos.Response.LogResponse;
import com.mealapp.backend.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("mealApp/api")
@CrossOrigin(origins = "http://localhost:4200")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @Operation(summary = "Get coupon by employee ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Coupon retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Booked_Response.class)) }),
            @ApiResponse(responseCode = "404", description = "Coupon not found",
                    content = @Content)
    })
    @GetMapping("/getCouponByID")
    public List<Booked_Response> getCoupon(@RequestParam Long emp_id) {
        return couponService.getCouponByID(emp_id);
    }

    @Operation(summary = "Delete a coupon")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Coupon deleted successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LogResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Coupon not found",
                    content = @Content)
    })
    @PutMapping("/DeleteCoupon")
    public ResponseEntity<?> deleteCoupon(@RequestBody DeleteCoupon deleteCoupon) {
        LogResponse isCancel = couponService.DeleteCouponByDateAndMealTypeAndEmpID(deleteCoupon);
        return ResponseEntity.ok(isCancel);
    }
}
