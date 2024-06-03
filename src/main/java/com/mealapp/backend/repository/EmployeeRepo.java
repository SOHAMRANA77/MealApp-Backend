package com.mealapp.backend.repository;

import com.mealapp.backend.entities.Employee;
import com.mealapp.backend.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@EnableJpaRepositories
@Repository
@Component
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    Employee findByEmail(String email);
    Optional<Employee> findByEmailAndCurrentPassword(String email, String password);
    Optional<Employee> findFirstByEmail(String email);

    Employee findByUserRole(UserRole userRole);

    @Query("SELECT e.name FROM Employee e WHERE e.id = :id")
    String findEmpNameById(@Param("id") Long id);
}
