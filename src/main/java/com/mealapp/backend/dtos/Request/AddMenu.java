package com.mealapp.backend.dtos.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AddMenu {
    @Schema(description = "Day of the week for the menu")
    private String day;

    @Schema(description = "Special date for the menu (if applicable)", example = "2024-06-22")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate specialDate;

    @Schema(description = "First menu item")
    private String item1;

    @Schema(description = "Second menu item")
    private String item2;

    @Schema(description = "Third menu item")
    private String item3;

    @Schema(description = "Fourth menu item")
    private String item4;

    @Schema(description = "Fifth menu item")
    private String item5;

    @Schema(description = "Type of menu (e.g., Breakfast, Lunch, Dinner)")
    private String MenuType;

    public AddMenu(String day, LocalDate specialDate, String item1, String item2, String item3, String item4, String item5, String type) {
        this.day = day;
        this.specialDate = specialDate;
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
        this.item4 = item4;
        this.item5 = item5;
        this.MenuType = type;
    }

    @Override
    public String toString() {
        return "AddMenu{" +
                "day='" + day + '\'' +
                ", specialDate=" + specialDate +
                ", item1='" + item1 + '\'' +
                ", item2='" + item2 + '\'' +
                ", item3='" + item3 + '\'' +
                ", item4='" + item4 + '\'' +
                ", item5='" + item5 + '\'' +
                ", MenuType='" + MenuType + '\'' +
                '}';
    }
}
