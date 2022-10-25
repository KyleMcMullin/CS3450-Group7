package com.cs3450.dansfrappesraps.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.cs3450.dansfrappesraps.ui.models.Drink
import com.cs3450.dansfrappesraps.ui.models.Ingredient
import com.cs3450.dansfrappesraps.ui.repositories.UserRepository

class CartScreenState{
    var priceSum by mutableStateOf(0.00)
    var userId by mutableStateOf("")
    var name by mutableStateOf("")
    var _ingredients = mutableStateListOf<Ingredient>()
    val ingredients: List<Ingredient> get() = _ingredients
    var _frappuccinos = mutableStateListOf<Drink>()
    val frappuccinos: List<Drink> get() = _frappuccinos
    var arrivalTime by mutableStateOf("")
    var checkBalance by mutableStateOf(false)
    var checkCart by mutableStateOf(false)
    var loading by mutableStateOf(false)

    fun addDrink(drink:Drink){
        _frappuccinos.add(drink)
    }
    fun addIngredients(ingredient: Ingredient){
        _ingredients.add(ingredient)
    }
}

class CartScreenViewModel(application: Application): AndroidViewModel(application) {
    val uiState = CartScreenState()

    suspend fun getDrinks(): List<Drink>{
        return uiState.frappuccinos
    }
    fun getIngredients(drink: Drink): List<Ingredient>{
        for(ingredients in drink.ingredients!!){
            if(!uiState.ingredients.contains(ingredients)) {
                uiState.addIngredients(ingredients)
            }
        }
        return uiState.ingredients
    }
    fun balanceCheck(){
        val userBalance = UserRepository.userBalance()
        if (userBalance != null) {
            uiState.checkBalance = userBalance >= uiState.priceSum
        }
        else{
            uiState.checkBalance = false
        }
    }
    fun calculateBalance(): Double{
        uiState.loading = true
        for(ingredient in uiState.ingredients){
            uiState.priceSum += ingredient.inventory!!.PPU!!
        }
        uiState.loading = false
        return uiState.priceSum
    }
    fun checkInventory(){

    }
    fun makeOrder(){

    }
    fun addDrinksToCart(drink: Drink){
        uiState.addDrink(drink)
    }

}