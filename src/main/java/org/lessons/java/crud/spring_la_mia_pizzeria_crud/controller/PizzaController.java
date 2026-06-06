package org.lessons.java.crud.spring_la_mia_pizzeria_crud.controller;

import java.util.List;

import org.lessons.java.crud.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.crud.spring_la_mia_pizzeria_crud.model.Promotion;
import org.lessons.java.crud.spring_la_mia_pizzeria_crud.repository.IngredientRepository;
import org.lessons.java.crud.spring_la_mia_pizzeria_crud.repository.PizzaRepository;
import org.lessons.java.crud.spring_la_mia_pizzeria_crud.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pizze")
public class PizzaController {

    @Autowired
    private PizzaRepository repository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private PromotionRepository promotionRepository;

    // Read
    @GetMapping
    public String index(Model model) {
        List<Pizza> pizze = repository.findAll();
        model.addAttribute("pizze", pizze);
        return "pizze/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("pizza", repository.findById(id).get());
        return "pizze/show";
    }

    @GetMapping("/searchByName")
    public String searchByName(@RequestParam(name = "name") String name, Model model) {

        List<Pizza> pizze;

        if (name != null && !name.isBlank()) {
            pizze = repository.findByNameContainingIgnoreCase(name);
        } else {
            pizze = repository.findAll();
        }

        model.addAttribute("pizze", pizze);

        return "pizze/index";
    }

    // Create
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("ingredients", ingredientRepository.findAll());
        return "pizze/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
        model.addAttribute("ingredients", ingredientRepository.findAll());

        if (bindingResult.hasErrors()) {
            return "pizze/create";
        }
        repository.save(formPizza);
        return "redirect:/pizze";

    }

    // Update
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("pizza", repository.findById(id).get());
        model.addAttribute("ingredients", ingredientRepository.findAll());

        return "pizze/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("pizza") Pizza formPizza,
            BindingResult bindingResult, Model model) {
        model.addAttribute("ingredients", ingredientRepository.findAll());

        if (bindingResult.hasErrors()) {
            return "pizze/edit";
        }
        repository.save(formPizza);
        return "redirect:/pizze";

    }

    // Delete
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        // prendere per ogni pizza le promozioni ad essa associate -> getPromotions()
        // eliminarle dalla tabella promotions: promotionRepository.delete(promotion)
        // a questo punto posso cancellare perchè non ho vincoli della foreign key su
        // pizza_id

        Pizza pizza = repository.findById(id).get();
        for (Promotion promotionToDelete : pizza.getPromotions()) {
            promotionRepository.delete(promotionToDelete);

        }

        // repository.deleteById(id);
        repository.delete(pizza);

        return "redirect:/pizze";
    }

    @GetMapping("/{id}/promotion")
    public String promotion(@PathVariable Integer id, Model model) {

        Promotion promotion = new Promotion();
        promotion.setPizza(repository.findById(id).get());
        model.addAttribute("promotion", promotion);

        return "promotions/create-or-edit";
    }

}
