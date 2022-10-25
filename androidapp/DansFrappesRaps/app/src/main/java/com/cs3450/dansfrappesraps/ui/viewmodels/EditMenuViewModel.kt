package com.cs3450.dansfrappesraps.ui.viewmodels

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.cs3450.dansfrappesraps.ui.models.Drink
import com.cs3450.dansfrappesraps.ui.models.Ingredient
import com.cs3450.dansfrappesraps.ui.repositories.DrinkImageRepository
import com.cs3450.dansfrappesraps.ui.repositories.DrinksRepository
import com.cs3450.dansfrappesraps.ui.repositories.IngredientsRepository

class EditMenuState() {
    var name by mutableStateOf("")
    var _ingredients = mutableStateListOf<Ingredient>()
    val ingredients: List<Ingredient> get() = _ingredients
    var loading by mutableStateOf(false)
    var isImage by mutableStateOf(false)
    var showImage by mutableStateOf(false)
    var image by mutableStateOf<Uri?>(null)
}

class EditMenuViewModel(application: Application): AndroidViewModel(application) {
    var uiState = EditMenuState()

    suspend fun setUpInitialState(id: String?) {
        if (id == null || id == "new") return
        val drink = DrinksRepository.getDrinks().find { it.id == id } ?: return
        uiState.name = drink.name ?: ""
        for (ingredient in drink.ingredients!!) {
            uiState._ingredients.removeIf { it.inventory?.id == ingredient.inventory?.id }
            uiState._ingredients.add(0, ingredient)
        }
    }

    fun incrementIngredient(ingredient: Ingredient) {
        uiState._ingredients[uiState._ingredients.indexOf(ingredient)] = ingredient.copy(count = ingredient.count?.plus(1))

    }

    fun decrementIngredient(ingredient: Ingredient) {
        if (uiState._ingredients.find(ingredient::equals)?.count!! > 0) {
            uiState._ingredients[uiState._ingredients.indexOf(ingredient)] = ingredient.copy(count = ingredient.count?.minus(1))
        }
    }

    suspend fun getIngredients() {
        uiState.loading = true
        uiState._ingredients.addAll(IngredientsRepository.getIngredients())
    }

    suspend fun addDrink() {
        val drink: Drink = Drink(
            name = uiState.name,
            ingredients = uiState._ingredients.filter { it.count!! > 0 },
            image = DrinkImageRepository.addImageToFirebaseStorage(uiState.image!!).toString()
        )
        DrinksRepository.newDrink(drink)

    }
    suspend fun updateDrink(id: String) {
        val drink: Drink = Drink(
            id = id,
            name = uiState.name,
            ingredients = uiState._ingredients.filter { it.count!! > 0 },
            image = DrinkImageRepository.addImageToFirebaseStorage(uiState.image!!).toString()
        )
        DrinksRepository.updateDrink(drink)
    }
    }