package com.mealapp.backend.service;


import com.mealapp.backend.entities.Department;

import java.util.List;

public interface departmentService {
    Department createDepartment(Department department);
    List<Department> getAllDepartments();
}
