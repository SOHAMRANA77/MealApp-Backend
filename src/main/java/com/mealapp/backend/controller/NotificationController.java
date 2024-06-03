package com.mealapp.backend.controller;

import com.mealapp.backend.dtos.Request.DeleteNotificationRequest;
import com.mealapp.backend.dtos.Response.LogResponse;
import com.mealapp.backend.dtos.Response.NotificationResponse;
import com.mealapp.backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("mealApp/api")
@CrossOrigin(origins = "http://localhost:4200")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/Notification{id}")
    public List<NotificationResponse> showNotificationByID(@RequestParam Long id){
        return notificationService.getNotificationById(id);
    }

    @PutMapping("/deleteNotification")
    public ResponseEntity<?> deleteNofi(@RequestBody DeleteNotificationRequest request){
        LogResponse notificationUpdate = notificationService.updateIsSeen(request.getId(), request.getEmpId());
        return ResponseEntity.ok(notificationUpdate);
    }

}
