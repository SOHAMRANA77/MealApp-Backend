package com.mealapp.backend.service.Implementation.JWT;

import com.mealapp.backend.entities.Employee;
import com.mealapp.backend.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EmployeeRepo employeeRepo;

    @Autowired
    public UserDetailsServiceImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Employee> optionalEmployee  =  employeeRepo.findFirstByEmail(email);
        System.out.println(optionalEmployee.toString());
        if(optionalEmployee.isEmpty())throw new UsernameNotFoundException("User not Found", null);
        return new org.springframework.security.core.userdetails.User(optionalEmployee.get().getEmail(),optionalEmployee.get().getCurrentPassword(),new ArrayList<>());
    }
}
