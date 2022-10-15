package com.cs3450.dansfrappesraps.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.cs3450.dansfrappesraps.ui.models.Inventory
import com.cs3450.dansfrappesraps.ui.repositories.InventoryRepository

class ManageInventoryState() {
    val _inventoryItems = mutableStateListOf<Inventory>()
    val inventory: List<Inventory> get() = _inventoryItems
    var loading by mutableStateOf(false)
}

class ManageInventoryViewModel(application: Application): AndroidViewModel(application) {
    val uiState = ManageInventoryState()

    suspend fun getInventory() {
        uiState.loading = true
        uiState._inventoryItems.clear()
        uiState._inventoryItems.addAll(InventoryRepository.getInventory())
        uiState.loading = false
    }

    suspend fun deleteInventory(inventory: Inventory) {
        InventoryRepository.deleteInventory(inventory.id!!)
        uiState._inventoryItems.remove(inventory)
    }
}