package com.mealapp.backend.controller;

import com.mealapp.backend.dtos.Request.BookingReq;
import com.mealapp.backend.dtos.Response.LogResponse;
import com.mealapp.backend.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mealApp/api")
@CrossOrigin(origins = "http://localhost:4200")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Operation(summary = "Add a new booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking added successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LogResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PostMapping("/addBooking")
    public ResponseEntity<?> addbooking(@RequestBody BookingReq bookingReq) {
        LogResponse bookingmessage = bookingService.addbooking(bookingReq);
        return ResponseEntity.ok(bookingmessage);
    }
}
