package com.mealapp.backend.dtos.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AddMenu {
    private String day;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate specialDate;
    private String item1;
    private String item2;
    private String item3;
    private String item4;
    private String item5;
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
