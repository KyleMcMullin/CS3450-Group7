package com.cs3450.dansfrappesraps.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.cs3450.dansfrappesraps.ui.repositories.SignUpException
import com.cs3450.dansfrappesraps.ui.repositories.UserRepository

class SignUpScreenState {
    var email by mutableStateOf("")
    var name by mutableStateOf("")
    var password by mutableStateOf("")
    var emailError by mutableStateOf(false)
    var nameError by mutableStateOf(false)
    var passwordError by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var signUpSuccess by mutableStateOf(false)
}

class SignUpViewModel(application: Application): AndroidViewModel(application) {
    val uiState = SignUpScreenState()

    suspend fun signUp() {
        // clear existing errors
        uiState.emailError = false
        uiState.passwordError = false
        uiState.nameError = false
        uiState.errorMessage = ""
        if (!uiState.email.contains("@")) {
            uiState.emailError = true
            uiState.errorMessage = "Email is invalid."
            return
        }

        if (uiState.password.length < 8) {
            uiState.passwordError = true
            uiState.errorMessage = "Password needs to be at least 8 characters."
            return
        }

        if (uiState.name == "") {
            uiState.nameError = true
            uiState.errorMessage = "Name must be input."
            return
        }

        try {
            UserRepository.createUser(uiState.email, uiState.password)
            uiState.signUpSuccess = true
        } catch (e: SignUpException) {
            uiState.errorMessage = e.message ?: "Something went wrong. Please try again."
        }
    }
}