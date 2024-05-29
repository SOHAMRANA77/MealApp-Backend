package com.mealapp.backend.entities;

import com.mealapp.backend.enums.CouponStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Data
@Setter
@Getter
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Enumerated(EnumType.STRING)
    private CouponStatus couponStatus = CouponStatus.INACTIVE;

    private LocalDate couponStamp;

    private String couponCode;

    private boolean isCancel = false;

    public Coupon() {}

    public Coupon(Booking booking, Menu menu, CouponStatus couponStatus, LocalDate couponStamp) {
        this.booking = booking;
        this.menu = menu;
        this.couponStatus = couponStatus;
        this.couponStamp = couponStamp;
    }

    public Coupon(String couponCode, LocalDate couponStamp, CouponStatus couponStatus, Menu menu, Booking booking) {
        this.couponCode = couponCode;
        this.couponStamp = couponStamp;
        this.couponStatus = couponStatus;
        this.menu = menu;
        this.booking = booking;
    }
}
