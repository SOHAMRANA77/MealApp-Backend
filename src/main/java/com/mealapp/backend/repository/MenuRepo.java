package com.mealapp.backend.repository;

import com.mealapp.backend.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface MenuRepo extends JpaRepository<Menu, Long> {
//    Menu findBySpecialDate(LocalDate specialDate);
//    Menu findByMenuDay(String day);

}
