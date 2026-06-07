package org.lessons.java.crud.spring_la_mia_pizzeria_crud.service;

import java.util.Optional;

import org.lessons.java.crud.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.crud.spring_la_mia_pizzeria_crud.model.Promotion;
import org.lessons.java.crud.spring_la_mia_pizzeria_crud.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    // create
    public Promotion create(Promotion promotion) {
        // aggiornamento solo di alcuni campi a seguito della creazione
        return promotionRepository.save(promotion);
    }

    // update
    public Promotion update(Promotion promotion) {
        // aggiornamento solo di altri campi a seguito dell'aggiornamento
        return promotionRepository.save(promotion);
    }

    // show
    public Promotion getById(Integer id) {
        Optional<Promotion> promotionAttempt = promotionRepository.findById(id);

        if (promotionAttempt.isEmpty()) {
            // lanciare not found con una response entity
        }

        return promotionAttempt.get();
    }
}
