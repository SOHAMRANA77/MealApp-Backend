package com.mealapp.backend.repository;

import com.mealapp.backend.dtos.Response.NotificationResponse;
import com.mealapp.backend.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification , Long> {
    List<Notification> findByEmployeeIdAndIsSeenFalse(Long id);
}
