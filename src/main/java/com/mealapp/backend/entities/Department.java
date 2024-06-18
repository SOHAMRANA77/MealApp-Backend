package com.mealapp.backend.entities;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Department ID", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Name of the department", example = "Finance")
    private String name;

    @Schema(description = "Location of the department", example = "New York")
    private String location;

    public Department(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public Department() {
    }
}
