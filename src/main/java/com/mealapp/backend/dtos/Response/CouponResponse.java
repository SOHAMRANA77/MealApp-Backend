package com.mealapp.backend.dtos.Response;

import com.mealapp.backend.enums.MenuType;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Map;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CouponResponse {
    private String empName;
    private String CouponCode;
    private LocalDate QrDate;
    private MenuType menuType;
//    private String QrData;
    private HttpStatus httpStatus;
    private boolean Status;
}