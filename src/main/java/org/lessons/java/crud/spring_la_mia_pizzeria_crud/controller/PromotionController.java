package org.lessons.java.crud.spring_la_mia_pizzeria_crud.controller;

// import org.lessons.java.crud.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.crud.spring_la_mia_pizzeria_crud.model.Promotion;
import org.lessons.java.crud.spring_la_mia_pizzeria_crud.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/promotions")
public class PromotionController {

    @Autowired
    private PromotionRepository repository;

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("promotion") Promotion formPromotion, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "promotions/create-or-edit";
        }

        repository.save(formPromotion);
        return "redirect:/pizze/" + formPromotion.getPizza().getId();
    }

    // iso un solo form per create ed edit al contrario di quanto fatto per il book
    // metodo che restituisca edit da compilare (con dati già inseriti)
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("promotion", repository.findById(id).get());
        model.addAttribute("edit", true);
        return "promotions/create-or-edit";
    }

    // metodo che effettui una update vera e propria
    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("promotion") Promotion formPromotion,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "promotions/create-or-edit";
        }
        repository.save(formPromotion);
        return "redirect:/pizze/" + formPromotion.getPizza().getId();

    }
}
