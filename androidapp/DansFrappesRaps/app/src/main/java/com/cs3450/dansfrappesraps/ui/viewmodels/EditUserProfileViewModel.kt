package com.cs3450.dansfrappesraps.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.cs3450.dansfrappesraps.ui.repositories.UserRepository

class EditUserProfileState {
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var nameError by mutableStateOf(false)
    var emailError by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
}

class EditUserProfileViewModel(application: Application): AndroidViewModel(application) {
    var uiState = EditUserProfileState()

    fun getUser() {
        val user = UserRepository.getCurrentUser()
        uiState.name = user.name ?: ""
        uiState.email = user.email ?: ""
    }

     suspend fun updateUser() {
         uiState.nameError = false
         uiState.emailError = false

         if (uiState.name.isBlank()) {
             uiState.nameError = true
             uiState.errorMessage = "Please enter a name"
             return
         } else if (uiState.email.isBlank()) {
             uiState.emailError = true
             uiState.errorMessage = "Please enter an email"
             return
         }
         val user = UserRepository.getCurrentUser()
         user.name = uiState.name
         user.email = uiState.email
         UserRepository.updateUser(user)
         UserRepository.editAuthUser(uiState.email)
     }
}