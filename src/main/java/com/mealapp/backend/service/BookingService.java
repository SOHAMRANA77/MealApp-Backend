package com.mealapp.backend.service;

import com.mealapp.backend.dtos.Request.BookingReq;
import com.mealapp.backend.dtos.Response.LogResponse;
import com.mealapp.backend.entities.Booking;

import java.util.List;

public interface BookingService {
    LogResponse addbooking(BookingReq bookingReq);
}
