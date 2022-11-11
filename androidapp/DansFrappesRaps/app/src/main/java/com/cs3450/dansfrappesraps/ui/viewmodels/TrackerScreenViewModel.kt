package com.cs3450.dansfrappesraps.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.cs3450.dansfrappesraps.ui.models.Order
import com.cs3450.dansfrappesraps.ui.repositories.OrdersRepository

class TrackerScreenState{
    var order by mutableStateOf(OrdersRepository.ordersCache)
    }

class TrackerScreenViewModel(application: Application): AndroidViewModel(application) {
    val uiState = TrackerScreenState()

    suspend fun setupScreen() {
        if (uiState.order == null) {
            uiState.order = Order()
        }
    }
}

