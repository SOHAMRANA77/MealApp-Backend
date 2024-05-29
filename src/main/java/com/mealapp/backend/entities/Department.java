package com.mealapp.backend.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"name", "location"})
})
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String location;

    public Department(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public Department() {
    }
}
