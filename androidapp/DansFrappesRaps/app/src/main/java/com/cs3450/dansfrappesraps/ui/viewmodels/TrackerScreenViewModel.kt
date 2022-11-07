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
import com.cs3450.dansfrappesraps.ui.repositories.CartRepository
import com.cs3450.dansfrappesraps.ui.repositories.UserRepository

class TrackerScreenState{
    var priceSum by mutableStateOf(0.000)
    }

class TrackerScreenViewModel(application: Application): AndroidViewModel(application) {
    val uiState = TrackerScreenState()

    suspend fun setupScreen() {
        if (uiState.cart == null) {
            uiState.cart = Cart()
        }
    }
}

