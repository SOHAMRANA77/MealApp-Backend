package com.mealapp.backend.dtos.Response;

import com.mealapp.backend.enums.MenuType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
public class Booked_Response {
    @Schema(description = "Date of the booking", example = "2024-06-22")
    private LocalDate bookingDate;

    @Schema(description = "Type of meal booked (e.g., Breakfast, Lunch, Dinner)")
    private MenuType mealType;

    public Booked_Response(LocalDate date, MenuType type) {
        this.bookingDate = date;
        this.mealType = type;
    }
}
