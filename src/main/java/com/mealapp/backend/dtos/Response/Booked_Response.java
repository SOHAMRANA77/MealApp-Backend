package com.mealapp.backend.dtos.Response;

import com.mealapp.backend.enums.MenuType;
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
    private LocalDate date;
    private MenuType type;

    public Booked_Response(LocalDate date, MenuType type) {
        this.date = date;
        this.type = type;
    }
}
