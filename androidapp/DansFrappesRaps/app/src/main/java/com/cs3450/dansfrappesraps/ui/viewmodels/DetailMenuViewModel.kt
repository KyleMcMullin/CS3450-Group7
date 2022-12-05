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
    var types by mutableStateOf(listOf<String>())
    var _customization = mutableStateListOf<Ingredient>()
    val customization: List<Ingredient> get() = _customization
    var loading by mutableStateOf(false)
    var type by mutableStateOf("")
    var drink by mutableStateOf(Drink())
    var quantity by mutableStateOf(0)
//    Need these?
    var errorMessage by mutableStateOf("")
    var newDrink by mutableStateOf(Drink())
    var selectedSize by mutableStateOf(Ingredient())
}


class DetailMenuViewModel (application: Application): AndroidViewModel(application){
    var uiState = DetailMenuState()
    private var radioOptions: Map<String, Ingredient> = mapOf("No sizes available" to Ingredient())
    suspend fun setUpInitialState(id: String, index: String) {
        setRadioOptions()
        var drink = if (id == "null") {
            if (index == "null") return
            OrdersRepository.getUnplacedOrder().drinks?.get(index.toInt()) ?: return
        } else {
            DrinksRepository.getDrinks().find {
                it.id == id } ?: return
        }
        uiState.drink = drink
        uiState.quantity = 1
        uiState.drinkName = drink.name.toString()
//        for (ingredient in drink.ingredients!!) {
//            uiState._customization.removeIf { it.inventory?.id == ingredient.inventory?.id }
//        }

        uiState._customization.forEach { it.count = 0 }
        drink.ingredients?.let { uiState._customization.addAll(it) }
        uiState.types = InventoryRepository.getTypes() + listOf("")
        var customizationCopy: MutableList<Ingredient> = mutableListOf()
        customizationCopy.addAll(uiState._customization)
        uiState._customization.forEach { ingredient ->
            if (ingredient.count!! > 0) {
                customizationCopy.removeIf {it.count == 0 && it.inventory?.id == ingredient.inventory?.id}
            }
        }
        uiState._customization.clear()
        uiState._customization.addAll(customizationCopy)
    }


    suspend fun getIngredients() {
        uiState.loading = true
        uiState._customization.addAll(IngredientsRepository.getIngredients())
    }

    fun addToCart(id: String?){
        uiState.selectedSize.count = 1
        uiState._customization.add(uiState.selectedSize)

        val drink = Drink(id = null, name=uiState.drinkName, ingredients=uiState._customization.filter { it.count!! > 0}, quantity=uiState.quantity)
        OrdersRepository.addDrinkToOrder(drink)

    }

    suspend fun setRadioOptions() {
        val sizes = IngredientsRepository.getSizes()
        radioOptions = sizes.associateBy { it.inventory?.name.toString() }
        uiState.selectedSize = sizes[0]
    }

    fun getRadioMap(): Map<String, Ingredient> {
        return radioOptions
    }

    fun isFavorite(): Boolean{
//        if(UserRepository.getCurrentUser().favorites == null) return false
        Log.e("Fav", UserRepository.getCurrentUser().favorites.toString())
        for(fav in UserRepository.getCurrentUser().favorites!!){
            if(uiState.drink.name == fav.name){
                return true
            }
        }
        return false
    }

    suspend fun removeFavorite(){
        UserRepository.removeFavorite(uiState.drink)
    }

    suspend fun addFavorite(){
        if(UserRepository.getCurrentUser().favorites == null) {
            Log.e("Null","Hello")
            UserRepository.getCurrentUser().favorites = mutableListOf()
            UserRepository.addFavorite(uiState.drink)
        }else{
            if (!isFavorite()) UserRepository.addFavorite(uiState.drink)
        }


    }
    fun hasIngredient(type: String): Boolean{
        if(getMatchType(type).size==0){
            return false
        }
        return true
    }

    fun setSize(sizeText: String) {
        uiState.selectedSize = radioOptions[sizeText]!!
    }

    fun isSelected(ingredient: Ingredient):Boolean{
        for(i in uiState.drink.ingredients!!){
            if(ingredient.inventory?.name == i.inventory?.name){
                return true
            }
        }
        return false
    }

    fun getMatchType(type: String): ArrayList<Ingredient> {
        val typeCustom:ArrayList<Ingredient> = ArrayList()
        for(inven in uiState._customization){
            if(inven.inventory?.type == type) {
                typeCustom.add(inven)
            }
        }
        return typeCustom
    }

    fun incrementIngredient(ingredient: Ingredient) {
        var index = uiState._customization.indexOf(ingredient)
        ingredient.apply {
            count = count?.plus(1)
        }
        uiState._customization[uiState._customization.indexOf(ingredient)] = ingredient//ingredient.copy(count = ingredient.count?.plus(1))

        Log.e("Error:" , uiState._customization[index].count.toString())
    }

    fun decrementIngredient(ingredient: Ingredient) {
        var index = uiState._customization.indexOf(ingredient)
        if (uiState._customization.find(ingredient::equals)?.count!! > 0) {
            ingredient.apply {
                count = count?.minus(1)
            }
            uiState._customization[uiState._customization.indexOf(ingredient)] = ingredient

        }
        Log.e("Error:" , uiState._customization[index].count.toString())
    }

    fun incrementQuantity(){
        uiState.quantity++
        Log.e("Error:" , uiState.quantity.toString())
    }

    fun decrementQuantity(){
        if(uiState.quantity>1){
            uiState.quantity--
        }
        Log.e("Error:" , uiState.quantity.toString())
    }
}