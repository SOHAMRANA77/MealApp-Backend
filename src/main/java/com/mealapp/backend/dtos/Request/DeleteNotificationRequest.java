package com.mealapp.backend.dtos.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteNotificationRequest {

    @Schema(description = "Notification ID to delete", example = "1")
    private Long id;

    @Schema(description = "Employee ID associated with the notification", example = "123")
    private Long empId;

    @Schema(description = "Message associated with the notification (optional)")
    private String message;
}
