package com.mealapp.backend.service.Implementation;

import com.mealapp.backend.dtos.Response.LogResponse;
import com.mealapp.backend.dtos.Response.NotificationResponse;
import com.mealapp.backend.entities.Employee;
import com.mealapp.backend.entities.Notification;
import com.mealapp.backend.enums.NotificationType;
import com.mealapp.backend.repository.NotificationRepo;
import com.mealapp.backend.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Notification_Implement implements NotificationService {

    @Autowired
    private NotificationRepo notificationRepo;

    @Override
    public List<NotificationResponse> getNotificationById(Long id) {
        return notificationRepo.findByEmployeeIdAndIsSeenFalse(id).stream()
                .map(notification -> new NotificationResponse(
                        notification.getMessage(),
                        notification.getId()    ,
                        notification.getType()))
                .collect(Collectors.toList());
    }

    @Override
    public void AddNotification(Employee employee, String message, NotificationType type){
        try{
            Notification notification = new Notification(message,type,employee);
            notificationRepo.save(notification);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public LogResponse updateIsSeen(Long id, Long empId) {
        try{
            Notification notification = notificationRepo.findByIdAndEmployeeIdAndIsSeenFalse(id, empId);
            System.out.println(notification);
            notification.setSeen(true);
            notificationRepo.save(notification);
            return new LogResponse("Done",true);
        }catch (Exception e){
            return new LogResponse("Notification : "+e.getMessage(),false);
        }
    }
}
