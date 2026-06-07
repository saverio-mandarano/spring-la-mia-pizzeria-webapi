package org.lessons.java.crud.spring_la_mia_pizzeria_crud.controller;

import java.util.List;
import java.util.Optional;

import org.lessons.java.crud.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.crud.spring_la_mia_pizzeria_crud.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/v2/pizze")
public class PizzaRestAdvancedController {

    @Autowired
    private PizzaService pizzaService;

    // Read
    @GetMapping
    public List<Pizza> index() {
        return pizzaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pizza> show(@PathVariable Integer id) {
        Optional<Pizza> pizzaAttempt = pizzaService.findById(id);

        if (pizzaAttempt.isEmpty()) {
            return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Pizza>(pizzaAttempt.get(), HttpStatus.OK);
    }

    // Create
    @PostMapping
    public ResponseEntity<Pizza> store(@Valid @RequestBody Pizza pizza) {
        return new ResponseEntity<Pizza>(pizzaService.create(pizza), HttpStatus.CREATED);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Pizza> update(@Valid @RequestBody Pizza pizza, @PathVariable Integer id) {
        if (pizzaService.findById(id).isEmpty()) {
            return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
        }
        // prima di fare la mia creazione, qualora io NON mi trovi nella situazione in
        // cui
        // il libro effettivamente è stato popolato con un id, vado a sovrascrivere l'id
        // del mio libro con l'id presente nel pathvariable.

        // in pratica:
        // l'id della risorsa lo decide l'url non il body
        pizza.setId(id);
        return new ResponseEntity<Pizza>(pizzaService.update(pizza), HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Pizza> delete(@PathVariable Integer id) {
        if (pizzaService.findById(id).isEmpty()) {
            return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
        }
        pizzaService.deleteById(id);
        return new ResponseEntity<Pizza>(HttpStatus.OK);
    }
}
