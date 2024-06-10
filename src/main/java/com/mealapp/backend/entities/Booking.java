package com.mealapp.backend.entities;

import com.mealapp.backend.enums.MenuType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Getter
@Setter
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Employee employee;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private MenuType bookingType;

    private boolean isCanceled = false;

    @OneToMany(mappedBy = "booking")
    private List<Coupon> coupons;

    public Booking() {
    }

    public Booking(Employee employee, LocalDate startDate, LocalDate endDate, MenuType bookingType) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bookingType = bookingType;
    }
}
