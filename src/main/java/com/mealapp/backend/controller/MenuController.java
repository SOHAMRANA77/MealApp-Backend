package com.mealapp.backend.controller;

import com.mealapp.backend.dtos.Request.AddMenu;
import com.mealapp.backend.entities.Menu;
import com.mealapp.backend.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("mealApp/api")
@CrossOrigin(origins = "http://localhost:4200")
public class MenuController {

    @Autowired
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/allMenu")
    public List<Menu> getMeal(){
        return menuService.getAllMenu();
    }

    @PostMapping("/addMenu")
    public Menu AddMeal(@RequestBody AddMenu addMenu){
        System.out.println(addMenu.toString());
        return menuService.addMenu(addMenu);
    }
}
