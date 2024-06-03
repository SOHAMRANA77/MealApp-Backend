package com.mealapp.backend.service;

import com.mealapp.backend.dtos.Response.LogResponse;
import com.mealapp.backend.dtos.Response.NotificationResponse;
import com.mealapp.backend.entities.Employee;
import com.mealapp.backend.entities.Notification;
import com.mealapp.backend.enums.NotificationType;

import java.util.List;

public interface NotificationService {

    List<NotificationResponse> getNotificationById(Long id);
    void AddNotification(Employee employee, String message, NotificationType type);

    LogResponse updateIsSeen(Long id, Long empId);
}
