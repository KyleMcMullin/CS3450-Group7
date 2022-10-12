package com.cs3450.dansfrappesraps.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.cs3450.dansfrappesraps.ui.repositories.InventoryRepository

class AdjustInventoryState{
    var name by mutableStateOf("")
    var nameError by mutableStateOf(false)
    var quantity by mutableStateOf("")
    var quantityError by mutableStateOf(false)
    var PPU by mutableStateOf("")
    var PPUError by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var adjustSuccess by mutableStateOf(false)
}

class AdjustInventoryViewModel(application: Application): AndroidViewModel(application) {
    var uiState = AdjustInventoryState()

    suspend fun adjustInventory(id: String?) {
        // clear existing errors
        uiState.nameError = false
        uiState.quantityError = false
        uiState.PPUError = false
        uiState.errorMessage = ""
        if (uiState.name == "") {
            uiState.nameError = true
            uiState.errorMessage = "Name is invalid."
            return
        }
        if (uiState.quantity == "") {
            uiState.quantityError = true
            uiState.errorMessage = "Quantity is invalid."
            return
        }
        if (uiState.PPU == "") {
            uiState.PPUError = true
            uiState.errorMessage = "Price per unit is invalid."
            return
        }
        InventoryRepository.editInventory(id!!, uiState.name, uiState.quantity.toDouble(), uiState.PPU.toInt())
        uiState.adjustSuccess = true
    }

    suspend fun deleteInventory(id: String?) {
        InventoryRepository.deleteInventory(id!!)
    }

    suspend fun addInventory() {
        // clear existing errors
        uiState.nameError = false
        uiState.quantityError = false
        uiState.PPUError = false
        uiState.errorMessage = ""
        if (uiState.name == "") {
            uiState.nameError = true
            uiState.errorMessage = "Name is invalid."
            return
        }
        if (uiState.quantity == "") {
            uiState.quantityError = true
            uiState.errorMessage = "Quantity is invalid."
            return
        }
        if (uiState.PPU == "") {
            uiState.PPUError = true
            uiState.errorMessage = "Price per unit is invalid."
            return
        }
        InventoryRepository.addInventory(uiState.name, uiState.quantity.toInt(), uiState.PPU.toDouble())
        uiState.adjustSuccess = true
    }

    suspend fun setUpInitialState(id: String?) {
        if (id != null) {
            val inventory = InventoryRepository.getInventory().find { it.id == id } ?: return
            uiState.name = inventory.name ?: ""
            uiState.quantity = inventory.quantity.toString()
            uiState.PPU = inventory.PPU.toString()
        }
    }
}