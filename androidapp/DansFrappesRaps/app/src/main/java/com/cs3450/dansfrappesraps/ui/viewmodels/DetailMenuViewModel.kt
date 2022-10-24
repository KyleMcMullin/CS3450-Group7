package com.cs3450.dansfrappesraps.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.cs3450.dansfrappesraps.ui.models.Ingredient
import com.cs3450.dansfrappesraps.ui.repositories.DrinksRepository
import com.cs3450.dansfrappesraps.ui.repositories.IngredientsRepository
import com.cs3450.dansfrappesraps.ui.repositories.InventoryRepository

class DetailMenuState(){
    var name by mutableStateOf("")
    var nameError by mutableStateOf(false)
    var quantity by mutableStateOf("")
    var quantityError by mutableStateOf(false)
    var type by mutableStateOf("")
    var typeError by mutableStateOf(false)
    var types by mutableStateOf(listOf<String>())
    var _ingredients = mutableStateListOf<Ingredient>()
    val ingredients: List<Ingredient> get() = _ingredients
    var loading by mutableStateOf(false)

//    Need these?
    var errorMessage by mutableStateOf("")
    var adjustSuccess by mutableStateOf(false)
    var dropDownExpanded by mutableStateOf(false)
}

class DetailMenuViewModel (application: Application): AndroidViewModel(application){
    var uiState = DetailMenuState()

    suspend fun setUpInitialState(id: String?) {
        if (id == null) return

        val drink = DrinksRepository.getDrinks().find { it.id == id } ?: return
        for (ingredient in drink.ingredients!!) {
            uiState._ingredients.removeIf { it.inventory?.id == ingredient.inventory?.id }
            uiState._ingredients.add(0, ingredient)
        }
        val inventory = InventoryRepository.getInventory().find { it.id == id } ?: return
        uiState.types = InventoryRepository.getTypes() + listOf("")
        uiState.name = inventory.name ?: ""
        uiState.quantity = inventory.quantity.toString()
        uiState.type = inventory.type ?: ""

    }

    suspend fun getIngredients() {
        uiState.loading = true
        uiState._ingredients.addAll(IngredientsRepository.getIngredients())
    }

    private fun runChecks(): Boolean {
        uiState.nameError = false
        uiState.quantityError = false
        uiState.errorMessage = ""
        if (uiState.name == "") {
            uiState.nameError = true
            uiState.errorMessage = "Name is invalid."
            return false
        }
        if (uiState.quantity == "") {
            uiState.quantityError = true
            uiState.errorMessage = "Quantity is invalid."
            return false
        }
        if (uiState.quantity.contains(".")) {
            uiState.quantityError = true
            uiState.errorMessage = "Quantity cannot contain decimals."
            return false
        }
        if (uiState.quantity.toInt() < 0) {
            uiState.quantityError = true
            uiState.errorMessage = "Quantity cannot be negative."
            return false
        }
        if (uiState.type == "") {
            uiState.typeError = true
            uiState.errorMessage = "Type is invalid."
            return false
        }
        return true
    }
}