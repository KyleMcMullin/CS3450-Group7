package com.cs3450.dansfrappesraps.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.cs3450.dansfrappesraps.ui.models.Cart
import com.cs3450.dansfrappesraps.ui.models.Drink
import com.cs3450.dansfrappesraps.ui.models.Ingredient
import com.cs3450.dansfrappesraps.ui.models.Inventory
import com.cs3450.dansfrappesraps.ui.repositories.CartRepository
import com.cs3450.dansfrappesraps.ui.repositories.InventoryRepository
import com.cs3450.dansfrappesraps.ui.repositories.OrdersRepository
import com.cs3450.dansfrappesraps.ui.repositories.UserRepository

class CartScreenState{
    var priceSum by mutableStateOf(0.00)
    var name by mutableStateOf("")
    var _ingredients = mutableStateListOf<Ingredient>()
    val ingredients: List<Ingredient> get() = _ingredients
    var _frappuccinos = mutableStateListOf<Drink>()
    val frappuccinos: List<Drink> get() = _frappuccinos
    var checkBalance by mutableStateOf(false)
    var checkCart by mutableStateOf(false)
    var loading by mutableStateOf(false)
    var cart by mutableStateOf(OrdersRepository.getUnplacedOrder())
    var errorMessage by mutableStateOf("")
    var checkoutSuccess by mutableStateOf(false)
    var cartDeletion by mutableStateOf(false)
    var drinkCount by mutableStateOf(0)
    var inventoryCheck by mutableStateOf(true)

    fun addDrink(drink:Drink){
        _frappuccinos.add(drink)
    }
    fun addIngredients(ingredient: Ingredient){
        _ingredients.add(ingredient)
    }
    fun clearDrinks(){
        _frappuccinos.clear()
    }
    fun clearIngredients(){
        _ingredients.clear()
    }
}

class CartScreenViewModel(application: Application): AndroidViewModel(application) {
    val uiState = CartScreenState()

    fun deletingCart() {
        uiState.priceSum = 0.00
        uiState.clearDrinks()
        uiState.clearIngredients()
        uiState.checkBalance = false
        uiState.checkCart = false
        uiState.errorMessage = ""
    }

    suspend fun setupScreen() {
        getCart()
        getDrinks()
        calculateBalance()
        uiState.clearIngredients()
    }
    fun getDrinks(): List<Drink> {
        var drinks = CartRepository.cartCache.drinks
        if (drinks != null) {
            for (drink: Drink in drinks) {
                if (!uiState.frappuccinos.contains(drink)) {
                    uiState.addDrink(drink)
                    uiState.drinkCount ++
                }
                else{
                    uiState.addDrink(drink)
                    uiState.drinkCount ++
                }
            }
            for(drink in drinks){
                getIngredients(drink)
            }
        }
        return uiState.frappuccinos
    }

    fun getCart(){
        uiState.cart = OrdersRepository.getUnplacedOrder()
    }
    fun getIngredients(drink: Drink): List<Ingredient> {
        for (ingredients in drink.ingredients!!) {
            uiState.addIngredients(ingredients)
        }
        return uiState.ingredients
    }

    fun balanceCheck(): Boolean {
        val userBalance = UserRepository.userBalance()
        if (userBalance != null) {
            uiState.checkBalance = userBalance >= uiState.priceSum
            return uiState.checkBalance
        } else {
            uiState.checkBalance = false
            return uiState.checkBalance
        }
    }

    fun calculateBalance(): Double {
        uiState.loading = true
        var coffeePrice = 0.00
        for (ingredient in uiState.ingredients) {
            coffeePrice += ingredient.inventory!!.PPU!!
        }
        uiState.priceSum = coffeePrice
        return uiState.priceSum
    }

    suspend fun checkInventory() {

    }

    suspend fun submitOrder() {
        OrdersRepository.placeOrder()
    }

    suspend fun checkout() {
        checkInventory()
        if (balanceCheck()) {
            submitOrder()
            UserRepository.subtractUserBalance(uiState.priceSum)
//            deletingCart()
            uiState.checkoutSuccess = true
            uiState.cartDeletion = true
        } else {
            uiState.errorMessage = "There is not enough money in your account, please add money and try again."
        }
    }
}

