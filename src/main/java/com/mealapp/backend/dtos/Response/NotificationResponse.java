package com.mealapp.backend.dtos.Response;

import com.mealapp.backend.enums.NotificationType;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
public class NotificationResponse {

    private String message;

    private NotificationType type;

    private boolean isSeen = false;

    public NotificationResponse(String message, NotificationType type, boolean isSeen) {
        this.message = message;
        this.type = type;
        this.isSeen = isSeen;
    }

    public NotificationResponse(String message, NotificationType type) {
        this.message = message;
        this.type = type;
    }
}
