package com.mealapp.backend.dtos.Request;
import com.mealapp.backend.enums.MenuType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingReq {
    private Long employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
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
