package com.mealapp.backend.dtos.Request;
import com.mealapp.backend.enums.MenuType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingReq {
    @Schema(description = "Employee ID for whom the booking is made", example = "1")
    private Long employeeId;

    @Schema(description = "Start date of the booking", example = "2024-06-22")
    private LocalDate startDate;

    @Schema(description = "End date of the booking", example = "2024-06-23")
    private LocalDate endDate;

    @Schema(description = "Type of booking (e.g., Breakfast, Lunch, Dinner)")
    private MenuType bookingType;

    public BookingReq() {
    }

    public BookingReq(Long employeeId, LocalDate startDate, LocalDate endDate, MenuType bookingType) {
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bookingType = bookingType;
    }

    @Override
    public String toString() {
        return "BookingReq{" +
                "employeeId=" + employeeId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", bookingType=" + bookingType +
                '}';
    }
}
