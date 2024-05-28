package com.mealapp.backend.entities;

import com.mealapp.backend.enums.MenuDay;
import com.mealapp.backend.enums.MenuType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Data
@Getter
@Setter
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Enumerated(EnumType.STRING)
    private MenuType menuType;

    @Temporal(TemporalType.DATE)
    private LocalDate specialDate;

    @Enumerated(EnumType.STRING)
    private MenuDay menuDay;

    private String item1;
    private String item2;
    private String item3;
    private String item4;
    private String item5;

    public Menu() {
    }

    @Override
    public String toString() {
        return "Menu{" +
                "Id=" + Id +
                ", menuType=" + menuType +
                ", specialDate=" + specialDate +
                ", menuDay=" + menuDay +
                ", item1='" + item1 + '\'' +
                ", item2='" + item2 + '\'' +
                ", item3='" + item3 + '\'' +
                ", item4='" + item4 + '\'' +
                ", item5='" + item5 + '\'' +
                '}';
    }

    public Menu(MenuType menuType, LocalDate mealDate, MenuDay menuDay, String item1, String item2, String item3, String item4, String item5) {
        this.menuType = menuType;
        this.specialDate = mealDate;
        this.menuDay = menuDay;
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
        this.item4 = item4;
        this.item5 = item5;
    }
}
