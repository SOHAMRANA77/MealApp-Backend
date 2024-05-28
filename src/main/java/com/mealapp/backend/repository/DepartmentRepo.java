package com.mealapp.backend.repository;

import com.mealapp.backend.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Long> {

    List<Department> findByName(String name);
}
