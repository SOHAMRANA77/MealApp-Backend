package com.mealapp.backend.service.Implementation;

import com.mealapp.backend.dtos.Request.RegisterEmployee;
import com.mealapp.backend.dtos.Response.LogResponse;
import com.mealapp.backend.entities.Department;
import com.mealapp.backend.entities.Employee;
import com.mealapp.backend.enums.UserRole;
import com.mealapp.backend.repository.DepartmentRepo;
import com.mealapp.backend.repository.EmployeeRepo;
import com.mealapp.backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class Employee_Implement implements EmployeeService {

    @Autowired
    private final EmployeeRepo employeeRepo;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final DepartmentRepo departmentRepository;

    public Employee_Implement(EmployeeRepo employeeRepo, PasswordEncoder passwordEncoder, DepartmentRepo departmentRepository) {
        this.employeeRepo = employeeRepo;
        this.passwordEncoder = passwordEncoder;
        this.departmentRepository = departmentRepository;
    }


    @PostConstruct
    public void createAdmin(){
        Employee admin = employeeRepo.findByUserRole(UserRole.ADMIN);
        if(admin == null){
            List<Department> departments = departmentRepository.findByName("JAVA");
            Department department = new Department();
            if (departments.isEmpty()) {
                department = new Department("JAVA","Vadodara");
                department = departmentRepository.save(department);
            } else if (departments.size() == 1) {
                department = departments.get(0);
            }
            Employee user = new Employee("admin","admin@admin.com","admin",department,"7069494809",UserRole.ADMIN);
            employeeRepo.save(user);
        }
    }

    @Override
    public LogResponse registerEmployee(RegisterEmployee registerEmployee) {
        try{
            List<Department> departments = departmentRepository.findByName(registerEmployee.getDepartment());
            Department department;

            if (departments.isEmpty()) {
                return new LogResponse("Department not found", HttpStatus.BAD_REQUEST, false);
            } else if (departments.size() == 1) {
                department = departments.get(0);
            } else {
                // Handle the case where multiple departments with the same name exist
                return new LogResponse("Multiple departments found with name: "+registerEmployee.getDepartment(), HttpStatus.BAD_REQUEST, false);
            }

            Employee employee = new Employee(registerEmployee.getName(),
                    registerEmployee.getEmail(),
                    this.passwordEncoder.encode(registerEmployee.getCurrentPassword()),
//                                        employeeDto.getCurrentPassword(),
                    department,
                    registerEmployee.getPhoneNo(),
                    registerEmployee.getUserRole());

            employeeRepo.save(employee);
            return new LogResponse("Registration Success", HttpStatus.CREATED, true);

        }catch (Exception e){
            return new LogResponse("Registration Failed", HttpStatus.BAD_REQUEST, false);
        }
    }

}
