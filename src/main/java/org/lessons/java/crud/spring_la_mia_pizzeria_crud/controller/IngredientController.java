package org.lessons.java.crud.spring_la_mia_pizzeria_crud.controller;

import org.lessons.java.crud.spring_la_mia_pizzeria_crud.model.Ingredient;
import org.lessons.java.crud.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.crud.spring_la_mia_pizzeria_crud.repository.IngredientRepository;
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
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    IngredientRepository ingredientRepository;

    // show all ingredients
    @GetMapping
    public String index(Model model) {
        model.addAttribute("ingredients", ingredientRepository.findAll());

        return "ingredients/index";

    }

    // show one ingredient
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("ingredient", ingredientRepository.findById(id).orElse(null));
        return "ingredients/show";
    }

    // create and store a new ingredient
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        return "/ingredients/create-or-edit";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("ingredient") Ingredient formIngredient, BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "ingredients/create-or-edit";
        }
        ingredientRepository.save(formIngredient);
        return "redirect:/ingredients";
    }

    // Edit and update an ingredient
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("ingredient", ingredientRepository.findById(id).get());
        model.addAttribute("edit", true);
        return "ingredients/create-or-edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("ingredient") Ingredient formIngredient,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "ingredients/create-or-edit";
        }

        ingredientRepository.save(formIngredient);
        // return "redirect:/ingredients";
        return "redirect:/ingredients/" + formIngredient.getId();

    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        // prendo l'ingrediende da eliminare con la repository usando il findById
        // dopodichè prendo le pizze collegate a questo ingrediente da eliminare tramite
        // getPizze()
        // prendo tutti gli ingrediendi di queste pizze (getIngredients() ) e gli
        // rimuovo il mio ingrediente remove(ingredienteToDelete)

        // solo a questo punto elimino con la repsitory il mio ingrediente dal db,
        // perchè so che non ci sono più pizze collegate ad esso

        Ingredient ingredientToDelete = ingredientRepository.findById(id).get();
        for (Pizza linkedPizza : ingredientToDelete.getPizze()) {
            linkedPizza.getIngredients().remove(ingredientToDelete);
        }

        ingredientRepository.delete(ingredientToDelete);

        return "redirect:/ingredients";
    }

}
