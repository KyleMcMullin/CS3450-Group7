package com.cs3450.dansfrappesraps.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.cs3450.dansfrappesraps.ui.repositories.UserRepository

class EmployeePayrollState {
    var name by mutableStateOf("")
    var balance by mutableStateOf("")
    var hours by mutableStateOf("")
    var payRate by mutableStateOf("")
    var hoursError by mutableStateOf(false)
    var saved by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var loading by mutableStateOf(true)
}

class EmployeePayrollViewModel(application: Application): AndroidViewModel(application) {
    val uiState = EmployeePayrollState()

    fun getUser() {
        UserRepository.getCurrentUser().let { user ->
            uiState.name = user.name?: ""
            uiState.balance = user.balance.toString()
            uiState.hours = user.hours.toString()
            uiState.payRate = user.payRate.toString()
        }
    }

    suspend fun updateHours() {
        try {
            uiState.hoursError = false
            if (uiState.hours.toInt() < 0) {
                uiState.hoursError = true
                uiState.errorMessage = "Hours cannot be negative."
                return
            }
            UserRepository.getCurrentUser().let { user ->
                user.hours = uiState.hours.toInt()
                UserRepository.updateUser(user)
            }
            uiState.saved = false
        } catch (e: Exception) {
            uiState.hoursError = true
            uiState.errorMessage = "Hours must be a number."
        }
    }
}