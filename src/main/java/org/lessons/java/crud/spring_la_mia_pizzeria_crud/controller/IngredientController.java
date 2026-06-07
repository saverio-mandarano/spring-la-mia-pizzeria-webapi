package org.lessons.java.crud.spring_la_mia_pizzeria_crud.controller;

import org.lessons.java.crud.spring_la_mia_pizzeria_crud.model.Ingredient;
import org.lessons.java.crud.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.crud.spring_la_mia_pizzeria_crud.repository.IngredientRepository;
import org.lessons.java.crud.spring_la_mia_pizzeria_crud.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    IngredientService ingredientService;

    // show all ingredients
    @GetMapping
    public String index(Model model) {
        model.addAttribute("ingredients", ingredientService.findAll());

        return "ingredients/index";

    }

    // show one ingredient
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("ingredient", ingredientService.getById(id));
        return "ingredients/show";
    }

    // create and store a new ingredientService
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("ingredientService", new Ingredient());
        return "/ingredients/create-or-edit";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("ingredientService") Ingredient formIngredient,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "ingredients/create-or-edit";
        }
        ingredientService.create(formIngredient);
        return "redirect:/ingredients";
    }

    // Edit and update an ingredientService
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("ingredientService", ingredientService.getById(id));
        model.addAttribute("edit", true);
        return "ingredients/create-or-edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("ingredientService") Ingredient formIngredient,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "ingredients/create-or-edit";
        }

        ingredientService.create(formIngredient);
        // return "redirect:/ingredients";
        return "redirect:/ingredients/" + formIngredient.getId();

    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {

        ingredientService.deleteById(id);

        return "redirect:/ingredients";
    }

}
