package com.cs3450.dansfrappesraps.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.cs3450.dansfrappesraps.ui.repositories.UserRepository

class ProfileScreenState {
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var isEmployee by mutableStateOf(false)
    var isManager by mutableStateOf(false)
    var balance by mutableStateOf("")
    var balanceError by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var isExpanded by mutableStateOf(false)
    var addBalance by mutableStateOf("")
}

class ProfileScreenViewModel(application: Application): AndroidViewModel(application) {
    var uiState = ProfileScreenState()

    fun getUser() {
        val user = UserRepository.getCurrentUser()
        uiState.name = user.name ?: ""
        uiState.email = user.email ?: ""
        uiState.isEmployee = user.employee ?: false
        uiState.isManager = user.manager ?: false
        uiState.balance = user.balance.toString()
    }

    suspend fun updateBalance() {
        uiState.balanceError = false
        uiState.errorMessage = ""
        if (uiState.addBalance.toDoubleOrNull() == null) {
            uiState.balanceError = true
            uiState.errorMessage = "Please enter a valid number"
        } else {
            UserRepository.addUserBalance(uiState.addBalance.toDouble())
            uiState.balance = UserRepository.getCurrentUser().balance.toString()
            uiState.isExpanded = false
            uiState.addBalance = ""
        }
    }

    suspend fun sendToBank() {
        val user = UserRepository.getCurrentUser()
        user.balance = 0.0
        uiState.balance = "0.0"
        UserRepository.sendToBank(user)
    }
}