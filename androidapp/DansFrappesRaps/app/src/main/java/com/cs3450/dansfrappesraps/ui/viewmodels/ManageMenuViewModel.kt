package com.cs3450.dansfrappesraps.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.cs3450.dansfrappesraps.ui.models.Drink
import com.cs3450.dansfrappesraps.ui.repositories.DrinksRepository

class ManageMenuState {
    var _drinks = mutableStateListOf<Drink>()
    val drinks: List<Drink> get() = _drinks
    var loading by mutableStateOf(false)
}

class ManageMenuViewModel(application: Application) : AndroidViewModel(application) {
    var uiState = ManageMenuState()

    suspend fun getDrinks() {
        uiState.loading = true
        uiState._drinks.clear()
        uiState._drinks.addAll(DrinksRepository.getAllDrinks())
    }

    suspend fun deleteDrink(drink: Drink) {
        DrinksRepository.deleteDrink(drink)
        uiState._drinks.remove(drink)
    }
}