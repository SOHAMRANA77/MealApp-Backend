package com.mealapp.backend.repository;

import com.mealapp.backend.entities.Booking;
import com.mealapp.backend.enums.MenuType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long> {
    @Query("SELECT b FROM Booking b WHERE b.employee.id = :empId AND :date BETWEEN b.startDate AND b.endDate")
    Optional<Booking> findByEmpIdAndDateInRange(@Param("empId") Long empId, @Param("date") LocalDate date);

    @Query("SELECT b FROM Booking b WHERE b.employee.id = :empId AND b.bookingType = :type AND :date BETWEEN b.startDate AND b.endDate")
    Optional<Booking> findByEmpIdAndBookingTypeAndDateInRange(@Param("empId") Long empId, @Param("date") LocalDate date, @Param("type")MenuType menuType);
}
