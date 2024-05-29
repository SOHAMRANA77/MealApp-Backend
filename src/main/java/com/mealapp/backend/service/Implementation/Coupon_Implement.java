package com.mealapp.backend.service.Implementation;

import com.mealapp.backend.dtos.Request.DeleteCoupon;
import com.mealapp.backend.dtos.Response.Booked_Response;
import com.mealapp.backend.dtos.Response.LogResponse;
import com.mealapp.backend.entities.Coupon;
import com.mealapp.backend.enums.MenuType;
import com.mealapp.backend.repository.CouponRepo;
import com.mealapp.backend.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Coupon_Implement implements CouponService {
    @Autowired
    private CouponRepo couponRepo;

//    @Override
//    public List<Booked_Response> getCouponByID(Long empId) {
//        List<Coupon> coupons = couponRepo.findByBookingEmployeeId(empId);
//        return coupons.stream()
//                .filter(coupon -> !coupon.isCancel())
//                .map(coupon -> new Booked_Response(coupon.getCouponStamp(), coupon.getBooking().getBookingType()))
//                .collect(Collectors.toList());
//    }
    @Override
    public List<Booked_Response> getCouponByID(Long empId) {
        List<Coupon> coupons = couponRepo.findByBookingEmployeeIdAndIsCancelFalse(empId);
        return coupons.stream()
                .map(coupon -> new Booked_Response(coupon.getCouponStamp(), coupon.getBooking().getBookingType()))
                .collect(Collectors.toList());
    }

    @Override
    public LogResponse DeleteCouponByDateAndMealTypeAndEmpID(DeleteCoupon deleteCoupon) {
        try{
            int task = couponRepo.cancelCouponsByDateEmployeeIdAndMealType(deleteCoupon.getId(),deleteCoupon.getDate(),deleteCoupon.getMenuType());
            return new LogResponse("Done", HttpStatus.ACCEPTED,true);
        }catch (Exception e){
            return new LogResponse("", HttpStatus.BAD_REQUEST,false);
        }
    }

    @Override
    public String getQRcdoe(Long empId, LocalDate date, MenuType type) {
        Coupon coupon = couponRepo.findByBooking_Employee_IdAndCouponStampAndMenu_MenuType(empId, date, type);

        // If coupon exists, return the coupon code
        if (coupon != null) {
            return coupon.getCouponCode();
        } else {
            // Coupon not found
            return "Coupon not found for emp_id=" + empId + ", date=" + date + ", type=" + type;
        }
    }
}
