package com.mealapp.backend.repository;

import com.mealapp.backend.entities.Booking;
import com.mealapp.backend.entities.Employee;
import com.mealapp.backend.enums.MenuType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long> {
//    @Query("SELECT b FROM Booking b WHERE b.employee.id = :empId AND :date BETWEEN b.startDate AND b.endDate")
//    Optional<Booking> findByEmpIdAndDateInRange(@Param("empId") Long empId, @Param("date") LocalDate date);
//
//    @Query("SELECT b FROM Booking b WHERE b.employee.id = :empId AND b.bookingType = :type AND :date BETWEEN b.startDate AND b.endDate")
//    Optional<Booking> findByEmpIdAndBookingTypeAndDateInRange(@Param("empId") Long empId, @Param("date") LocalDate date, @Param("type")MenuType menuType);
//
//    List<Booking> findByEmployeeAndBookingTypeAndStartDateBetweenOrEndDateBetween(Employee employee, MenuType bookingType, LocalDate startDate, LocalDate endDate, LocalDate endDate1, LocalDate startDate1);
//
//    List<Booking> findByEmpIdAndBookingType(Long id, MenuType bookingType);
//
//    @Query("SELECT b FROM Booking b WHERE b.employee.id = :employeeId AND b.bookingType = :bookingType AND b.startDate <= :endDate AND b.endDate >= :startDate")
//    List<Booking> findByEmployeeIdAndBookingTypeAndDateRange(
//            @Param("employeeId") Long employeeId,
//            @Param("bookingType") MenuType bookingType,
//            @Param("startDate") LocalDate startDate,
//            @Param("endDate") LocalDate endDate
//    );

    @Query("SELECT b FROM Booking b WHERE b.employee.id = :empId AND :date BETWEEN b.startDate AND b.endDate")
    Optional<Booking> findByEmpIdAndDateInRange(@Param("empId") Long empId, @Param("date") LocalDate date);

    @Query("SELECT b FROM Booking b WHERE b.employee.id = :empId AND b.bookingType = :type AND :date BETWEEN b.startDate AND b.endDate")
    Optional<Booking> findByEmpIdAndBookingTypeAndDateInRange(@Param("empId") Long empId, @Param("date") LocalDate date, @Param("type") MenuType menuType);

//    @Query("SELECT b FROM Booking b WHERE b.employee.id = :employeeId AND b.bookingType = :bookingType AND b.startDate <= :endDate AND b.endDate >= :startDate")
//    List<Booking> findByEmployeeIdAndBookingTypeAndDateRange(
//            @Param("employeeId") Long employeeId,
//            @Param("bookingType") MenuType bookingType,
//            @Param("startDate") LocalDate startDate,
//            @Param("endDate") LocalDate endDate
//    );

    @Query("SELECT b FROM Booking b WHERE b.employee.id = :employeeId AND b.bookingType = :bookingType")
    List<Booking> findByEmployeeIdAndBookingType(
            @Param("employeeId") Long employeeId,
            @Param("bookingType") MenuType bookingType
    );

    List<Booking> findByEmployeeIdAndBookingTypeAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndIsCanceled(
            Long employeeId,
            MenuType bookingType,
            LocalDate startDate,
            LocalDate endDate,
            boolean isCanceled
    );

    @Query("SELECT b FROM Booking b " +
            "JOIN b.coupons c " +
            "WHERE b.employee.id = :employeeId " +
            "AND b.bookingType = :bookingType " +
            "AND b.startDate <= :endDate " +
            "AND b.endDate >= :startDate " +
            "AND c.isCancel = false")
    List<Booking> findByEmployeeIdAndBookingTypeAndDateRangeIncludingActiveCoupons(
            @Param("employeeId") Long employeeId,
            @Param("bookingType") MenuType bookingType,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
    @Query("SELECT b FROM Booking b WHERE b.employee.id = :employeeId AND b.bookingType = :bookingType " +
            "AND b.startDate <= :endDate AND b.endDate >= :startDate AND b.isCanceled = false")
    List<Booking> findByEmployeeIdAndBookingTypeAndDateRange(
            @Param("employeeId") Long employeeId,
            @Param("bookingType") MenuType bookingType,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
