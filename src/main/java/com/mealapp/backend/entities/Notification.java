package com.mealapp.backend.entities;

import com.mealapp.backend.enums.NotificationType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@Setter
@Getter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private NotificationType type;

    private boolean isSeen = false;

    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Employee employee;

    public Notification(String message, NotificationType type, Employee employee) {
        this.message = message;
        this.type = type;
        this.employee = employee;
    }
}
