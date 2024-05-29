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
import com.mealapp.backend.repository.BookingRepo;
import com.mealapp.backend.repository.CouponRepo;
import com.mealapp.backend.repository.EmployeeRepo;
import com.mealapp.backend.repository.MenuRepo;
import com.mealapp.backend.service.BookingService;
import com.mealapp.backend.service.Implementation.UUID.CouponCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
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

    @Override
    public LogResponse addbooking(BookingReq bookingReq) {
        try {
            // Check if employee exists
            System.out.println(bookingReq.toString());
            Employee employee = employeeRepo.findById(bookingReq.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
            System.out.println(employee.toString());

            // Create a new Booking entity from DTO
            Booking booking = new Booking(employee, bookingReq.getStartDate(), bookingReq.getEndDate(), bookingReq.getBookingType());
            System.out.println(booking.toString());

            Optional<Booking> S_booked = bookingRepo.findByEmpIdAndBookingTypeAndDateInRange(employee.getId(),bookingReq.getStartDate(),bookingReq.getBookingType());
            Optional<Booking> E_booked = bookingRepo.findByEmpIdAndBookingTypeAndDateInRange(employee.getId(),bookingReq.getEndDate(),bookingReq.getBookingType());
            // Save the booking
            if(!S_booked.isPresent() || !E_booked.isPresent()){
                bookingRepo.save(booking);
                generateCouponsForWeekdays(booking);
                return new LogResponse("Booking saved successfully",true);
            }else {
                boolean S_couponsExist = couponRepo.existsByBookingAndCouponStampBetweenAndIsCancelFalse(S_booked.get(), booking.getStartDate(), booking.getEndDate());
                boolean E_couponsExist = couponRepo.existsByBookingAndCouponStampBetweenAndIsCancelFalse(E_booked.get(), booking.getStartDate(), booking.getEndDate());
                if (E_couponsExist) {
                    // Coupons exist, set cancel status to false for existing coupons
                    couponRepo.updateCancelStatusForBookingInRange(E_booked.get(), booking.getStartDate(), booking.getEndDate());
                    return new LogResponse("Coupons reactivated successfully", true);
                } else if(S_couponsExist){
                    couponRepo.updateCancelStatusForBookingInRange(S_booked.get(), booking.getStartDate(), booking.getEndDate());
                    return new LogResponse("Coupons reactivated successfully", true);}
                else {
                    // Coupons not found, return message
                    return new LogResponse("Booking already in database but no active coupons found", true);
                }
            }
        } catch (Exception e) {
            return new LogResponse("Error occurred while booking: " + e.getMessage(), false);
        }
    }

    private void generateCouponsForWeekdays(Booking booking) {
        // Get the start date of the booking
        LocalDate startDate = booking.getStartDate();

        // Generate coupons for each weekday between start and end dates
        LocalDate currentDate = startDate;
        while (currentDate.isBefore(booking.getEndDate()) || currentDate.isEqual(booking.getEndDate())) {
            // Check if the current day is Monday to Friday
            if (currentDate.getDayOfWeek().getValue() >= DayOfWeek.MONDAY.getValue() &&
                    currentDate.getDayOfWeek().getValue() <= DayOfWeek.FRIDAY.getValue()) {
                // Generate coupon for this weekday
                generateCoupon(booking, currentDate,booking.getBookingType());
            }
            // Move to the next day
            currentDate = currentDate.plusDays(1);
        }
    }

    private void generateCoupon(Booking booking, LocalDate date, MenuType bookingType) {
        // Find the appropriate meal based on specific date or day of week
        Menu meal = findMealForDate(date,bookingType);
        if (meal == null) {
            throw new RuntimeException("Meal not found for date: " + date);
        }
        //create jwt token here and then add string
        // Create a coupon entity and save it
        String prefix = bookingType == MenuType.LUNCH ? "L" : "D";
        String Code = prefix+couponCodeGenerator.generateUniqueCouponCode()+booking.getId()+couponCodeGenerator.generateUniqueCouponCode();
        Coupon coupon = new Coupon(Code,date, CouponStatus.INACTIVE,meal,booking);
        couponRepo.save(coupon);
    }
    private Menu findMealForDate(LocalDate date,MenuType bookingType) {
        Menu meal = menuRepo.findBySpecialDateAndMenuType(date,bookingType);
        if (meal != null) {
            return meal;
        }

        // If no meal for the specific date, find by the day of the week
        String dayOfWeek = date.getDayOfWeek().toString();
        return menuRepo.findByMenuDayAndMenuType(MenuDay.valueOf(dayOfWeek),bookingType);
    }

}
