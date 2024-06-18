package com.mealapp.backend.dtos.Response;

import com.mealapp.backend.enums.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
public class NotificationResponse {

    @Schema(description = "Message content of the notification")
    private String message;

    @Schema(description = "Unique identifier of the notification", example = "1")
    private Long id;

    @Schema(description = "Type of notification (e.g., EMAIL, SMS)")
    private NotificationType type;

    @Schema(description = "Flag indicating whether the notification has been seen by the user", defaultValue = "false")
    private boolean isSeen;

    public NotificationResponse(String message, Long id, NotificationType type) {
        this.message = message;
        this.id = id;
        this.type = type;
    }

    public NotificationResponse(String message, NotificationType type) {
        this.message = message;
        this.type = type;
    }
}
