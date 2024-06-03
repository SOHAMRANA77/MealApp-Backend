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
    private Long id;

    private NotificationType type;

    private boolean isSeen = false;

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
