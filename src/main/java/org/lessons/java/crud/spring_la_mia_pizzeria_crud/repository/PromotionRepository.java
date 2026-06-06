package org.lessons.java.crud.spring_la_mia_pizzeria_crud.repository;

import org.lessons.java.crud.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.crud.spring_la_mia_pizzeria_crud.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Integer> {

}
