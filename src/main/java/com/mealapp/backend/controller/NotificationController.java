package com.mealapp.backend.controller;

import com.mealapp.backend.dtos.Request.DeleteNotificationRequest;
import com.mealapp.backend.dtos.Response.LogResponse;
import com.mealapp.backend.dtos.Response.NotificationResponse;
import com.mealapp.backend.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get notifications by employee ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notifications retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NotificationResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Notifications not found",
                    content = @Content)
    })
    @GetMapping("/Notification{id}")
    public List<NotificationResponse> showNotificationByID(@RequestParam Long id) {
        return notificationService.getNotificationById(id);
    }

    @Operation(summary = "Delete a notification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification deleted successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LogResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Notification not found",
                    content = @Content)
    })
    @PutMapping("/deleteNotification")
    public ResponseEntity<?> deleteNotification(@RequestBody DeleteNotificationRequest request) {
        LogResponse notificationUpdate = notificationService.updateIsSeen(request.getId(), request.getEmpId());
        return ResponseEntity.ok(notificationUpdate);
    }

    @Operation(summary = "Delete all notifications for an employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All notifications deleted successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LogResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = @Content)
    })
    @PutMapping("/deleteAllNotification")
    public ResponseEntity<?> deleteAllNotification(@RequestBody DeleteNotificationRequest request) {
        LogResponse notificationUpdate = notificationService.updateAllIsSeen(request.getEmpId());
        return ResponseEntity.ok(notificationUpdate);
    }
}
