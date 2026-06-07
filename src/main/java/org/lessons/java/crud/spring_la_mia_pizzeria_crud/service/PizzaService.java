package org.lessons.java.crud.spring_la_mia_pizzeria_crud.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.lessons.java.crud.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.crud.spring_la_mia_pizzeria_crud.model.Promotion;
import org.lessons.java.crud.spring_la_mia_pizzeria_crud.repository.PizzaRepository;
import org.lessons.java.crud.spring_la_mia_pizzeria_crud.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PizzaService {
    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private PromotionRepository promotionRepository;

    // index
    public List<Pizza> findAll() {
        return pizzaRepository.findAll();
    }

    public List<Pizza> findAllSortedByPrice() {
        return pizzaRepository.findAll(Sort.by("price"));
    }

    public List<Pizza> findAllSortedByName() {
        return pizzaRepository.findAll(Sort.by("name"));
    }

    public Optional<Pizza> findById(Integer id) {
        return pizzaRepository.findById(id);
    }

    // show
    public Pizza getById(Integer id) {
        Optional<Pizza> pizzaAttempt = pizzaRepository.findById(id);

        if (pizzaAttempt.isEmpty()) {
            // lanciare not found con una response entity
        }

        return pizzaAttempt.get();
    }

    public List<Pizza> findByName(String name) {
        return pizzaRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Pizza> findByPrice(BigDecimal price) {
        return pizzaRepository.findByPrice(price);
    }

    public List<Pizza> findByNameOrDescription(String query) {
        return pizzaRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query);
    }

    // create
    public Pizza create(Pizza pizza) {
        // aggiornamento solo di alcuni campi a seguito della creazione
        return pizzaRepository.save(pizza);
    }

    // update
    public Pizza update(Pizza pizza) {
        // aggiornamento solo di altri campi a seguito dell'aggiornamento
        return pizzaRepository.save(pizza);
    }

    // delete
    public void delete(Pizza pizza) {
        // prendere per ogni pizza le promozioni ad essa associate -> getPromotions()
        // eliminarle dalla tabella promotions: promotionRepository.delete(promotion)
        // a questo punto posso cancellare perchè non ho vincoli della foreign key su
        // pizza_id
        for (Promotion promotionToDelete : pizza.getPromotions()) {
            promotionRepository.delete(promotionToDelete);
        }

        pizzaRepository.delete(pizza);
    }

    public void deleteById(Integer id) {
        // Pizza pizza = repository.findById(id).get();
        // oppure:
        Pizza pizza = getById(id);

        for (Promotion promotionToDelete : pizza.getPromotions()) {
            promotionRepository.delete(promotionToDelete);
        }

        pizzaRepository.delete(pizza);
    }

    // exists
    public Boolean existsById(Integer id) {
        return pizzaRepository.existsById(id);
    }

    public Boolean exists(Pizza pizza) {
        // return repository.existsById(pizza.getId());
        // già esiste il metodo quindi non richiamo la repository, ed inoltre
        // andando a modificare il metodo existsById si modifica anche exists in
        // automatico:
        return existsById(pizza.getId());
    }

}
