package com.mealapp.backend.service;

import com.mealapp.backend.dtos.Request.AddMenu;
import com.mealapp.backend.entities.Menu;

import java.time.LocalDate;
import java.util.List;

public interface MenuService {
    List<Menu> getAllMenu();
    Menu addMenu(AddMenu menu);
    Menu getMealForBookingDay(LocalDate bookingDate);

}
