package com.mealapp.backend.service.Implementation;

import com.mealapp.backend.dtos.Request.RegisterEmployee;
import com.mealapp.backend.dtos.Response.LogResponse;
import com.mealapp.backend.entities.Employee;
import com.mealapp.backend.enums.UserRole;
import com.mealapp.backend.repository.EmployeeRepo;
import com.mealapp.backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class Employee_Implement implements EmployeeService {

    @Autowired
    private final EmployeeRepo employeeRepo;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public Employee_Implement(EmployeeRepo employeeRepo, PasswordEncoder passwordEncoder) {
        this.employeeRepo = employeeRepo;
        this.passwordEncoder = passwordEncoder;
    }


    @PostConstruct
    public void createAdmin(){
        Employee admin = employeeRepo.findByUserRole(UserRole.ADMIN);
        if(admin == null){
            Employee user = new Employee("admin","admin@admin.com","admin","IT","7069494809",UserRole.ADMIN);
            employeeRepo.save(user);
        }
    }

    @Override
    public LogResponse registerEmployee(RegisterEmployee registerEmployee) {
        try{
            Employee employee = new Employee(registerEmployee.getName(),
                    registerEmployee.getEmail(),
                    this.passwordEncoder.encode(registerEmployee.getCurrentPassword()),
//                                        employeeDto.getCurrentPassword(),
                    registerEmployee.getDepartment(),
                    registerEmployee.getPhoneNo(),
                    registerEmployee.getUserRole());

            employeeRepo.save(employee);
            return new LogResponse("Registration Success", HttpStatus.CREATED, true);

        }catch (Exception e){
            return new LogResponse("Registration Failed", HttpStatus.BAD_REQUEST, false);
        }
    }

}
