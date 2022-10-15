package com.cs3450.dansfrappesraps.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.cs3450.dansfrappesraps.ui.models.Inventory

class EditMenuState() {
    var name by mutableStateOf("")
    val _ingredients = mutableStateListOf<Inventory>()
    val ingredients: List<Inventory> get() = _ingredients
    //
}

class EditMenuViewModel(application: Application): AndroidViewModel(application) {
    var uiState = EditMenuState()

    fun setUpInitialState(id: String?) {
        if (id == null) return
//        val drink = uiState.ingredients?.find { it.id == id } ?: return
//        uiState.name = drink.name ?: ""
//        uiState.ingredients = drink.ingredients ?: mutableListOf()
    }

    fun addInventory() {
        uiState._ingredients.add(Inventory())
    }
}