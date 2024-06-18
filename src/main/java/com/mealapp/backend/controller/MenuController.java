package com.mealapp.backend.controller;

import com.mealapp.backend.dtos.Request.AddMenu;
import com.mealapp.backend.entities.Menu;
import com.mealapp.backend.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get all menu items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menu items retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Menu.class)) }),
            @ApiResponse(responseCode = "404", description = "Menu items not found",
                    content = @Content)
    })
    @GetMapping("/allMenu")
    public List<Menu> getMeal() {
        return menuService.getAllMenu();
    }

    @Operation(summary = "Add a new menu item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menu item added successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Menu.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid menu item details",
                    content = @Content)
    })
    @PostMapping("/addMenu")
    public Menu AddMeal(@RequestBody AddMenu addMenu) {
        System.out.println(addMenu.toString());
        return menuService.addMenu(addMenu);
    }
}
