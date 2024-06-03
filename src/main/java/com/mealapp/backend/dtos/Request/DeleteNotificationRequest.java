package com.mealapp.backend.dtos.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteNotificationRequest {
    private Long id;
    private Long empId;
    private String message;
}
