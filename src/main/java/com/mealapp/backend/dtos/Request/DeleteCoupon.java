package com.mealapp.backend.dtos.Request;

import com.mealapp.backend.enums.MenuType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteCoupon {

    @Schema(description = "ID of the coupon to delete")
    private Long id;

    @Schema(description = "Date associated with the coupon")
    private LocalDate date;

    @Schema(description = "Type of menu associated with the coupon")
    private MenuType menuType;

    @Override
    public String toString() {
        return "DeleteCoupon{" +
                "id=" + id +
                ", date=" + date +
                ", menuType=" + menuType +
                '}';
    }
}
