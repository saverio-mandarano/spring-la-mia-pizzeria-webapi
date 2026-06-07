package org.lessons.java.crud.spring_la_mia_pizzeria_crud.service;

import java.util.List;
import java.util.Optional;

import org.lessons.java.crud.spring_la_mia_pizzeria_crud.model.Ingredient;
import org.lessons.java.crud.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.crud.spring_la_mia_pizzeria_crud.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    /// index
    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    // show
    public Ingredient getById(Integer id) {
        Optional<Ingredient> ingredientAttempt = ingredientRepository.findById(id);
        if (ingredientAttempt.isEmpty()) {
            // lanciare not found con una response entity
        }

        return ingredientAttempt.get();
    }

    // create
    public Ingredient create(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    // update
    public Ingredient update(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    // delete
    public void delete(Ingredient ingredient) {
        // prendo l'ingrediende da eliminare con la repository usando il findById
        // dopodichè prendo le pizze collegate a questo ingrediente da eliminare tramite
        // getPizze()
        // prendo tutti gli ingrediendi di queste pizze (getIngredients() ) e gli
        // rimuovo il mio ingrediente remove(ingredienteToDelete)

        // solo a questo punto elimino con la repsitory il mio ingrediente dal db,
        // perchè so che non ci sono più pizze collegate ad esso
        if (ingredient.getPizze() != null) {
            for (Pizza linkedPizza : ingredient.getPizze()) {
                linkedPizza.getIngredients().remove(ingredient);
            }
        }

        ingredientRepository.delete(ingredient);

    }

    public void deleteById(Integer id) {

        Ingredient ingredient = getById(id);
        delete(ingredient);
    }

}
