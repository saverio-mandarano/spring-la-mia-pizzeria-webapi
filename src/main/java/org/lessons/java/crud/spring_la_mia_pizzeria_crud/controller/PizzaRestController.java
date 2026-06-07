package org.lessons.java.crud.spring_la_mia_pizzeria_crud.controller;

import java.util.List;

import org.lessons.java.crud.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.crud.spring_la_mia_pizzeria_crud.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pizze")
public class PizzaRestController {

    @Autowired
    private PizzaService pizzaService;

    // Read
    @GetMapping
    public List<Pizza> index() {
        return pizzaService.findAll();
    }

    @GetMapping("/{id}")
    public Pizza show(@PathVariable Integer id) {
        return pizzaService.getById(id);
    }

    // Create
    @PostMapping
    public Pizza store(@Valid @RequestBody Pizza pizza) {
        return pizzaService.create(pizza);
    }

    // Update
    @PutMapping("/{id}")
    public Pizza update(@Valid @RequestBody Pizza pizza, @PathVariable Integer id) {
        // prima di fare la mia creazione, qualora io NON mi trovi nella situazione in
        // cui
        // il libro effettivamente è stato popolato con un id, vado a sovrascrivere l'id
        // del mio libro con l'id presente nel pathvariable.
        pizza.setId(id);
        return pizzaService.update(pizza);
    }

    // Delete
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        pizzaService.deleteById(id);
    }

}
