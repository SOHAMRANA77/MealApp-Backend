package com.mealapp.backend.dtos.Request;

import com.mealapp.backend.enums.MenuType;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteCoupon {
    private Long id;
    private LocalDate date;
    private MenuType menuType;
}
