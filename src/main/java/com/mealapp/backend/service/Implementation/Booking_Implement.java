package com.mealapp.backend.service.Implementation;

import com.mealapp.backend.dtos.Request.BookingReq;
import com.mealapp.backend.dtos.Response.LogResponse;
import com.mealapp.backend.entities.Booking;
import com.mealapp.backend.entities.Coupon;
import com.mealapp.backend.entities.Employee;
import com.mealapp.backend.entities.Menu;
import com.mealapp.backend.enums.CouponStatus;
import com.mealapp.backend.enums.MenuDay;
import com.mealapp.backend.enums.MenuType;
import com.mealapp.backend.enums.NotificationType;
import com.mealapp.backend.repository.BookingRepo;
import com.mealapp.backend.repository.CouponRepo;
import com.mealapp.backend.repository.EmployeeRepo;
import com.mealapp.backend.repository.MenuRepo;
import com.mealapp.backend.service.BookingService;
import com.mealapp.backend.service.Implementation.UUID.CouponCodeGenerator;
import com.mealapp.backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class Booking_Implement implements BookingService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private MenuRepo menuRepo;

    @Autowired
    private CouponRepo couponRepo;

    @Autowired
    private CouponCodeGenerator couponCodeGenerator;

    @Autowired
    private NotificationService notificationService;

    @Override
    public LogResponse addbooking(BookingReq bookingReq) {
        String msg;
        String date;

        try {
            if (!isBookingAllowed(bookingReq)) {
                msg = "Booking is not allowed at this time for " + bookingReq.getBookingType().toString().toLowerCase();
                return new LogResponse(msg, false);
            }

            Employee employee = employeeRepo.findById(bookingReq.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));

            if (bookingReq.getStartDate().equals(bookingReq.getEndDate())) {
                date = " on " + bookingReq.getStartDate();
            } else {
                date = " on " + bookingReq.getStartDate() + " to " + bookingReq.getEndDate();
            }

            Booking booking = new Booking(employee, bookingReq.getStartDate(), bookingReq.getEndDate(), bookingReq.getBookingType());

            Optional<Booking> S_booked = bookingRepo.findByEmpIdAndBookingTypeAndDateInRange(employee.getId(), bookingReq.getStartDate(), bookingReq.getBookingType());
            Optional<Booking> E_booked = bookingRepo.findByEmpIdAndBookingTypeAndDateInRange(employee.getId(), bookingReq.getEndDate(), bookingReq.getBookingType());

            if (!S_booked.isPresent() || !E_booked.isPresent()) {
                bookingRepo.save(booking);
                generateCouponsForWeekdays(booking);
                msg = "Booking saved successfully for " + bookingReq.getBookingType().toString().toLowerCase() + date;
                notificationService.AddNotification(employee, msg, NotificationType.BOOKED);
                return new LogResponse(msg, true);
            } else {
                boolean S_couponsExist = couponRepo.existsByBookingAndCouponStampBetweenAndIsCancelTrue(S_booked.get(), booking.getStartDate(), booking.getEndDate());
                boolean E_couponsExist = couponRepo.existsByBookingAndCouponStampBetweenAndIsCancelTrue(E_booked.get(), booking.getStartDate(), booking.getEndDate());

                if (E_couponsExist) {
                    couponRepo.updateCancelStatusForBookingInRange(E_booked.get(), booking.getStartDate(), booking.getEndDate());
                    msg = "Coupons reactivated successfully " + bookingReq.getBookingType().toString().toLowerCase() + date;
                    notificationService.AddNotification(E_booked.get().getEmployee(), msg, NotificationType.BOOKED);
                    return new LogResponse(msg, true);
                } else if (S_couponsExist) {
                    couponRepo.updateCancelStatusForBookingInRange(S_booked.get(), booking.getStartDate(), booking.getEndDate());
                    msg = "Coupons reactivated successfully " + bookingReq.getBookingType().toString().toLowerCase() + date;
                    notificationService.AddNotification(S_booked.get().getEmployee(), msg, NotificationType.BOOKED);
                    return new LogResponse(msg, true);
                } else {
                    return new LogResponse("Booking already in database but no active coupons found", true);
                }
            }
        } catch (Exception e) {
            if (bookingReq.getStartDate().equals(bookingReq.getEndDate())) {
                date = " on " + bookingReq.getStartDate();
            } else {
                date = " on " + bookingReq.getStartDate() + " to " + bookingReq.getEndDate();
            }
            msg = "Booking already in database for " + bookingReq.getBookingType().toString().toLowerCase() + date;
            return new LogResponse(msg, false);
        }
    }

    private boolean isBookingAllowed(BookingReq bookingReq) {
        LocalDateTime now = LocalDateTime.now();
        if (bookingReq.getBookingType() == MenuType.LUNCH) {
            LocalDateTime cutOff = LocalDateTime.of(now.toLocalDate(), LocalTime.of(21, 0));
            if (now.isAfter(cutOff)) {
                return false;
            }
        } else if (bookingReq.getBookingType() == MenuType.DINNER) {
            LocalDateTime cutOff = LocalDateTime.of(now.toLocalDate(), LocalTime.of(14, 0));
            if (now.isAfter(cutOff)) {
                return false;
            }
        }
        return true;
    }

    private void generateCouponsForWeekdays(Booking booking) {
        LocalDate startDate = booking.getStartDate();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(booking.getEndDate())) {
            if (currentDate.getDayOfWeek().getValue() >= DayOfWeek.MONDAY.getValue() &&
                    currentDate.getDayOfWeek().getValue() <= DayOfWeek.FRIDAY.getValue()) {
                generateCoupon(booking, currentDate, booking.getBookingType());
            }
            currentDate = currentDate.plusDays(1);
        }
    }

    private void generateCoupon(Booking booking, LocalDate date, MenuType bookingType) {
        Menu meal = findMealForDate(date, bookingType);
        if (meal == null) {
            throw new RuntimeException("Meal not found for date: " + date);
        }

        String prefix = bookingType == MenuType.LUNCH ? "L" : "D";
        String code = prefix + couponCodeGenerator.generateUniqueCouponCode() + booking.getId() + couponCodeGenerator.generateUniqueCouponCode();
        Coupon coupon = new Coupon(code, date, CouponStatus.INACTIVE, meal, booking);
        couponRepo.save(coupon);
    }

    private Menu findMealForDate(LocalDate date, MenuType bookingType) {
        Menu meal = menuRepo.findBySpecialDateAndMenuType(date, bookingType);
        if (meal != null) {
            return meal;
        }

        String dayOfWeek = date.getDayOfWeek().toString();
        return menuRepo.findByMenuDayAndMenuType(MenuDay.valueOf(dayOfWeek), bookingType);
    }
}
