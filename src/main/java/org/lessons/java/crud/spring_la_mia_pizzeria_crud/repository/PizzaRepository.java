package org.lessons.java.crud.spring_la_mia_pizzeria_crud.repository;

import java.math.BigDecimal;
import java.util.List;

import org.lessons.java.crud.spring_la_mia_pizzeria_crud.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
    public List<Pizza> findByNameContainingIgnoreCase(String name);

    public List<Pizza> findByPrice(BigDecimal price);

    public List<Pizza> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);
}