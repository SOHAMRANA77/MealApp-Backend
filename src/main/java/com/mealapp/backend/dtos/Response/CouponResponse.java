package com.mealapp.backend.dtos.Response;

import com.mealapp.backend.enums.MenuType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CouponResponse {
    @Schema(description = "Employee Name", example = "John Doe")
    private String empName;

    @Schema(description = "Coupon Code", example = "ABC123XYZ")
    private String CouponCode;

    @Schema(description = "QR Date", example = "2024-06-18")
    private LocalDate QrDate;

    @Schema(description = "Menu Type", example = "LUNCH")
    private MenuType menuType;

    @Schema(description = "HTTP Status", example = "FOUND")
    private HttpStatus httpStatus;

    @Schema(description = "Status", example = "true")
    private boolean Status;
}
