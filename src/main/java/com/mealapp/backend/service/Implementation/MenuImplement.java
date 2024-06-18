package com.mealapp.backend.service.Implementation;

import com.mealapp.backend.dtos.Request.AddMenu;
import com.mealapp.backend.entities.Menu;
import com.mealapp.backend.enums.MenuDay;
import com.mealapp.backend.enums.MenuType;
import com.mealapp.backend.repository.MenuRepo;
import com.mealapp.backend.service.MenuService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@NoArgsConstructor
public class MenuImplement implements MenuService {

    @Autowired
    private MenuRepo menuRepo;

    @Override
    public List<Menu> getAllMenu() {
        return menuRepo.findAll();
    }

    @Override
    public Menu addMenu(AddMenu menu) {
        System.out.println("Received AddMenu: " + menu.toString());
        Menu addmenu = new Menu(MenuType.valueOf(menu.getMenuType()),menu.getSpecialDate(), MenuDay.valueOf(menu.getDay()),menu.getItem1(),menu.getItem2(),menu.getItem3(),menu.getItem4(),menu.getItem5());
        return menuRepo.save(addmenu);
    }
    @Override
    public Menu getMealForBookingDay(LocalDate bookingDate) {
        Long mealId = Long.valueOf(getMealIdForBookingDay(bookingDate));
        return menuRepo.findById(mealId).orElse(null);
    }

    private int getMealIdForBookingDay(LocalDate bookingDate) {
        switch (bookingDate.getDayOfWeek()) {
            case MONDAY:
                return 1;
            case TUESDAY:
                return 2;
            case WEDNESDAY:
                return 3;
            case THURSDAY:
                return 4;
            case FRIDAY:
                return 5;
            default:
                return 0;
        }
    }


}
