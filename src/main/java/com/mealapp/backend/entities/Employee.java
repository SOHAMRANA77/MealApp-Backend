package com.mealapp.backend.entities;

import com.mealapp.backend.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employee")
@Data
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "empName", nullable = false)
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String currentPassword;

    @Column(name = "oldPassword1")
    private String oldPassword1;

    @Column(name = "oldPassword2")
    private String oldPassword2;

    @Column(name = "oldPassword3")
    private String oldPassword3;

    @Column(name = "oldPassword4")
    private String oldPassword4;

    @ManyToOne
    @JoinColumn(name = "dept_id")
    private Department department;

    @Column(name = "phoneNo")
    private String phoneNo;

    @Column(name ="userRole")
    private UserRole userRole;

    public Employee() {
    }

    public Employee(String name, String email, String currentPassword, Department department, String phoneNo, UserRole userRole) {
        this.name = name;
        this.email = email;
        this.currentPassword = currentPassword;
        this.department = department;
        this.phoneNo = phoneNo;
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", currentPassword='" + currentPassword + '\'' +
                ", department='" + department + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}
