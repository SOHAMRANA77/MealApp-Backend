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
import java.util.List;
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
            // Validate booking constraints
            if (!isBookingAllowed(bookingReq)) {
                msg = "Booking is not allowed at this time for " + bookingReq.getBookingType().toString().toLowerCase();
                return new LogResponse(msg, false);
            }

            Employee employee = employeeRepo.findById(bookingReq.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));

            if (bookingReq.getStartDate().equals(bookingReq.getEndDate())) {
                date = " on " + bookingReq.getStartDate();
            } else {
                date = " from " + bookingReq.getStartDate() + " to " + bookingReq.getEndDate();
            }

            // Check for existing bookings
            List<Booking> existingBookings = bookingRepo.findByEmployeeIdAndBookingTypeAndDateRange(
                    employee.getId(), bookingReq.getBookingType(), bookingReq.getStartDate(), bookingReq.getEndDate());
            if (!existingBookings.isEmpty()) {
                msg = "Booking already exists for " + bookingReq.getBookingType().toString().toLowerCase() + date;
                return new LogResponse(msg, false);
            }

            Booking newBooking = new Booking(employee, bookingReq.getStartDate(), bookingReq.getEndDate(), bookingReq.getBookingType());

            // Save the new booking first
            bookingRepo.save(newBooking);

            // Handle overlapping bookings
            handleCouponsForOverlappingBookings(existingBookings, newBooking);

            // Generate coupons for weekdays
            generateCouponsForWeekdays(newBooking);

            msg = "Booking saved successfully for " + bookingReq.getBookingType().toString().toLowerCase() + date;
            notificationService.AddNotification(employee, msg, NotificationType.BOOKED);
            return new LogResponse(msg, true);

        } catch (Exception e) {
            if (bookingReq.getStartDate().equals(bookingReq.getEndDate())) {
                date = " on " + bookingReq.getStartDate();
            } else {
                date = " from " + bookingReq.getStartDate() + " to " + bookingReq.getEndDate();
            }
            msg = "Booking failed for " + bookingReq.getBookingType().toString().toLowerCase() + date + ": " + e.getMessage();
            return new LogResponse(msg, false);
        }
    }

    private boolean isBookingAllowed(BookingReq bookingReq) {
        LocalDate now = LocalDate.now();
        LocalDate startDate = bookingReq.getStartDate();

        LocalDateTime currentDateTime = LocalDateTime.now();
        if (bookingReq.getBookingType() == MenuType.LUNCH) {
            LocalDateTime cutOff = LocalDateTime.of(now, LocalTime.of(21, 0));
            if (currentDateTime.isAfter(cutOff) && startDate.equals(now)) {
                return false;
            }
        } else if (bookingReq.getBookingType() == MenuType.DINNER) {
            LocalDateTime cutOff = LocalDateTime.of(now, LocalTime.of(14, 0));
            if (currentDateTime.isAfter(cutOff) && startDate.equals(now)) {
                return false;
            }
        }
        return true;
    }

    private void handleCouponsForOverlappingBookings(List<Booking> overlappingBookings, Booking newBooking) {
        for (Booking existingBooking : overlappingBookings) {
            List<Coupon> existingCoupons = couponRepo.findByBooking(existingBooking);

            for (Coupon coupon : existingCoupons) {
                if (!coupon.getCouponStamp().isBefore(newBooking.getStartDate()) &&
                        !coupon.getCouponStamp().isAfter(newBooking.getEndDate())) {
                    coupon.setBooking(newBooking);
                    couponRepo.save(coupon);
                } else {
                    coupon.setCancel(true);
                    couponRepo.save(coupon);
                }
            }
        }
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
