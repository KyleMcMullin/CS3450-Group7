package com.cs3450.dansfrappesraps.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.cs3450.dansfrappesraps.ui.repositories.UserRepository

class RootNavigationState {
    var isManager by mutableStateOf(false)
    var isEmployee by mutableStateOf(false)
}

class RootNavigationViewModel(application: Application): AndroidViewModel(application) {
    val uiState = RootNavigationState()
    fun signOutUser() {
        UserRepository.signOutUser()
    }

    fun initialSetup() {
        uiState.isManager = UserRepository.isUserManager()
        uiState.isEmployee = UserRepository.isUserEmployee()
    }

    fun isUserLoggedIn(): Boolean {
        return UserRepository.isUserLoggedIn()
    }
}