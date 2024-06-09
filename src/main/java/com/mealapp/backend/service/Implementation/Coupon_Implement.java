package com.mealapp.backend.service.Implementation;

import com.mealapp.backend.dtos.Request.DeleteCoupon;
import com.mealapp.backend.dtos.Response.Booked_Response;
import com.mealapp.backend.dtos.Response.BookingsResponse;
import com.mealapp.backend.dtos.Response.CouponResponse;
import com.mealapp.backend.dtos.Response.LogResponse;
import com.mealapp.backend.entities.Coupon;
import com.mealapp.backend.entities.Employee;
import com.mealapp.backend.enums.CouponStatus;
import com.mealapp.backend.enums.MenuType;
import com.mealapp.backend.enums.NotificationType;
import com.mealapp.backend.repository.CouponRepo;
import com.mealapp.backend.repository.EmployeeRepo;
import com.mealapp.backend.service.CouponService;
import com.mealapp.backend.service.EmployeeService;
import com.mealapp.backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Coupon_Implement implements CouponService {
    @Autowired
    private CouponRepo couponRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private NotificationService notificationService;


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
            Employee employee = employeeRepo.findById(deleteCoupon.getId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
            int task = couponRepo.cancelCouponsByDateEmployeeIdAndMealType(deleteCoupon.getId(),deleteCoupon.getDate(),deleteCoupon.getMenuType());
            String msg = "Coupon Successfully Deleted for "+deleteCoupon.getMenuType()+" on "+deleteCoupon.getDate();
            notificationService.AddNotification(employee,msg, NotificationType.BOOKING_CANCEL);
            return new LogResponse("Coupon Successfully Deleted", HttpStatus.ACCEPTED,true);
        }catch (Exception e){
            return new LogResponse(e.getMessage(), HttpStatus.BAD_REQUEST,false);
        }
    }

    @Override
    public ResponseEntity<?> getQRcdoe(Long empId, LocalDate date, MenuType type) {
        Employee employee = employeeRepo.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Coupon coupon = couponRepo.findByBooking_Employee_IdAndCouponStampAndMenu_MenuType(empId, date, type);
        String name = employeeService.getFullName(empId);
        // If coupon exists, return the coupon code
        if (coupon != null) {
            if(coupon.getCouponStatus() == CouponStatus.REDEEM){
                return ResponseEntity.ok(new LogResponse("Coupon is Already Used",false));
            }
            String msg = "Coupon Successfully "+CouponStatus.REDEEM.toString().toLowerCase()+" for "+type+" on "+date;
            notificationService.AddNotification(employee,msg, NotificationType.BOOKING_CANCEL);
            coupon.setCouponStatus(CouponStatus.REDEEM);
            couponRepo.save(coupon);
            return ResponseEntity.ok(new CouponResponse(name,coupon.getCouponCode(),coupon.getCouponStamp(),coupon.getBooking().getBookingType(), HttpStatus.FOUND,true));
        } else {
            // Coupon not found
            return ResponseEntity.ok(new LogResponse("Coupon not found for emp_id=" + empId + ", date=" + date + ", type=" + type,false));
        }
    }
    @Scheduled(cron = "0 * * * * *")
    public void updateCouponStatuses() {
        List<Coupon> allBookings = couponRepo.findAll();
        LocalDate today = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        for (Coupon booking : allBookings) {
            if (booking.getCouponStamp().isEqual(today)) {
                if (booking.getBooking().getBookingType() == MenuType.LUNCH) {
                    if (currentTime.isAfter(LocalTime.of(12, 0)) && currentTime.isBefore(LocalTime.of(13, 0))) {
                        booking.setCouponStatus(CouponStatus.ACTIVE);
                    } else if (currentTime.isAfter(LocalTime.of(13, 0))) {
                        booking.setCouponStatus(CouponStatus.DISCARD);
                    }
                } else if (booking.getBooking().getBookingType() == MenuType.DINNER) {
                    if (currentTime.isAfter(LocalTime.of(19, 0)) && currentTime.isBefore(LocalTime.of(20, 0))) {
                        booking.setCouponStatus(CouponStatus.ACTIVE);
                    } else if (currentTime.isAfter(LocalTime.of(20, 0))) {
                        booking.setCouponStatus(CouponStatus.DISCARD);
                    }
                }
                couponRepo.save(booking);
            }
        }
    }
}
