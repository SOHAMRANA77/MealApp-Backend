package com.mealapp.backend.repository;
import com.mealapp.backend.entities.Booking;

import com.mealapp.backend.entities.Coupon;
import com.mealapp.backend.entities.Employee;
import com.mealapp.backend.enums.MenuType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CouponRepo extends JpaRepository<Coupon, Long> {
//    List<Coupon> findByBookingEmployeeId(Long empId);
    List<Coupon> findByBookingEmployeeIdAndIsCancelFalse(Long empId);
    boolean existsByBookingAndCouponStampBetweenAndIsCancelTrue(Booking booking, LocalDate startDate, LocalDate endDate);

    @Modifying
    @Transactional
    @Query("UPDATE Coupon c SET c.isCancel = true WHERE c.booking.employee.id = :empId AND c.couponStamp = :date AND c.booking.bookingType = :mealType")
    int cancelCouponsByDateEmployeeIdAndMealType(Long empId, LocalDate date, com.mealapp.backend.enums.MenuType mealType);

    @Modifying
    @Transactional
    @Query("UPDATE Coupon c SET c.isCancel = false WHERE c.booking = :booking AND c.couponStamp BETWEEN :startDate AND :endDate")
    void updateCancelStatusForBookingInRange(@Param("booking") Booking booking, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    Coupon findByBooking_Employee_IdAndCouponStampAndMenu_MenuType(Long empId, LocalDate date, MenuType type);

    List<Coupon> findByBookingAndCouponStamp(Booking booking, LocalDate date);

    List<Coupon> findByBookingEmployeeAndBookingBookingTypeAndCouponStampBetween(Employee employee, MenuType bookingType, LocalDate cancelDate, LocalDate cancelDate1);

//    List<Coupon> findByBooking(Booking booking);

    @Query("SELECT c FROM Coupon c WHERE c.booking = :booking AND c.isCancel = false")
    List<Coupon> findByBooking(@Param("booking") Booking booking);
}
