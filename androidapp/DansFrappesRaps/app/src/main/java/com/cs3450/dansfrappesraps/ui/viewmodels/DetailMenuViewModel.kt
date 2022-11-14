package com.cs3450.dansfrappesraps.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.cs3450.dansfrappesraps.ui.models.Drink
import com.cs3450.dansfrappesraps.ui.models.Ingredient
import com.cs3450.dansfrappesraps.ui.repositories.*

class DetailMenuState {
    var drinkName by mutableStateOf("")
    var nameError by mutableStateOf(false)
    var types by mutableStateOf(listOf<String>())
    var _customization = mutableStateListOf<Ingredient>()
    val customization: List<Ingredient> get() = _customization
    var loading by mutableStateOf(false)
    var type by mutableStateOf("")
//    Need these?
    var errorMessage by mutableStateOf("")
    var newDrink by mutableStateOf(Drink())
}


class DetailMenuViewModel (application: Application): AndroidViewModel(application){
    var uiState = DetailMenuState()

    suspend fun setUpInitialState(id: String?) {
        if (id == null) return

        val drink = DrinksRepository.getDrinks().find { it.id == id } ?: return
        uiState.drinkName = drink.name.toString()
        for (ingredient in drink.ingredients!!) {
            uiState._customization.removeIf { it.inventory?.id == ingredient.inventory?.id }
        }
        uiState.types = InventoryRepository.getTypes() + listOf("")
    }

    suspend fun getIngredients() {
        uiState.loading = true
        var ingredients : MutableList<Ingredient> = IngredientsRepository.getIngredients()
        for(i in ingredients){
                runChecks()
        }
        uiState._customization.addAll(IngredientsRepository.getIngredients())
    }

    fun addToCart(id: String?){
        val drink = Drink(id = null, name=uiState.drinkName, ingredients=uiState.customization/*, quantity=uiState.quantity*/)
        OrdersRepository.addDrinkToOrder(drink)
//        val drink = DrinksRepository.getDrinks().find { it.id == id } ?: return
//        CartRepository.addDrink(drink)

    }

    fun hasIngredient(type: String): Boolean{
        if(getMatchType(type).size==0){
            return false
        }
        return true
    }

    fun getMatchType(type: String): ArrayList<Ingredient> {
        val typeCustom:ArrayList<Ingredient> = ArrayList()
        for(inven in uiState._customization){
            if(inven.inventory?.type == type){
                typeCustom.add(inven)
            }
        }
        return typeCustom
    }
    private fun runChecks(): Boolean {
        uiState.nameError = false
        uiState.errorMessage = ""
        if (uiState.drinkName == "") {
            uiState.nameError = true
            uiState.errorMessage = "Name is invalid."
            return false
        }
        return true
    }
}