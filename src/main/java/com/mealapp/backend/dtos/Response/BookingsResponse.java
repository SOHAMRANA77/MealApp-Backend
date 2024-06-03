package com.mealapp.backend.dtos.Response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Data
@Getter
@Setter
@NoArgsConstructor
public class BookingsResponse {
    private Map<String, MealType> bookings;

    @Data
    @Getter
    @Setter
    @NoArgsConstructor
    public static class MealType {
        private boolean LUNCH;
        private boolean DINNER;
    }
}