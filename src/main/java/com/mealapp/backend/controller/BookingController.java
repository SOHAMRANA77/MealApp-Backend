package com.mealapp.backend.controller;

import com.mealapp.backend.dtos.Request.BookingReq;
import com.mealapp.backend.dtos.Response.LogResponse;
import com.mealapp.backend.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mealApp/api")
@CrossOrigin(origins = "http://localhost:4200")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/addBooking")
    public ResponseEntity<?> addbooking(@RequestBody BookingReq bookingReq){
        LogResponse bookingmessage = bookingService.addbooking(bookingReq);
        System.out.println(bookingmessage.toString()+" \n\n\n\n\n");
        return ResponseEntity.ok(bookingmessage);
    }
}
