package com.mealapp.backend.service.Implementation;

import com.mealapp.backend.entities.Department;
import com.mealapp.backend.repository.DepartmentRepo;
import com.mealapp.backend.service.departmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class departmentImplement implements departmentService {

    @Autowired
    private DepartmentRepo departmentRepository;


    @Override
    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
}
