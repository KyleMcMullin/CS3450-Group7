package com.cs3450.dansfrappesraps.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.cs3450.dansfrappesraps.ui.models.User
import com.cs3450.dansfrappesraps.ui.repositories.SignUpException
import com.cs3450.dansfrappesraps.ui.repositories.UserRepository

class CreateNewUserScreenState {
    var email by mutableStateOf("")
    var name by mutableStateOf("")
    var password by mutableStateOf("")
    var emailError by mutableStateOf(false)
    var nameError by mutableStateOf(false)
    var passwordError by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var signUpSuccess by mutableStateOf(false)
    var isManager by mutableStateOf(false)
    var isEmployee by mutableStateOf(false)
    var dropdownExpanded by mutableStateOf(false)
    var userType by mutableStateOf(UserTypes.CUSTOMER)

    companion object UserTypes {
        const val MANAGER = "Manager"
        const val EMPLOYEE = "Employee"
        const val CUSTOMER = "Customer"
    }
}
class CreateNewUserViewModel(application: Application): AndroidViewModel(application) {
    val uiState = CreateNewUserScreenState()
    var id: String? = null
    var user: User? = null

    suspend fun setupInitialState(id: String?) {
        if (id == null || id == "new") return
        this.id = id
        val user = UserRepository.getAllUsers().find {it.id == id} ?: return
        this.user = user
        uiState.email = user.email ?: ""
        uiState.isEmployee = user.employee ?: false
        uiState.isManager = user.manager ?: false
        uiState.name = user.name ?: ""
        uiState.userType = when {
            uiState.isManager -> CreateNewUserScreenState.UserTypes.MANAGER
            uiState.isEmployee -> CreateNewUserScreenState.UserTypes.EMPLOYEE
            else -> CreateNewUserScreenState.UserTypes.CUSTOMER
        }
    }

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

        if (uiState.userType == CreateNewUserScreenState.UserTypes.MANAGER) {
            uiState.isManager = true
        } else if (uiState.userType == CreateNewUserScreenState.UserTypes.EMPLOYEE) {
            uiState.isEmployee = true
        }

        try {
            UserRepository.createDifferentUser(uiState.email, uiState.password, uiState.name, uiState.isManager, uiState.isEmployee)
            uiState.signUpSuccess = true
        } catch (e: SignUpException) {
            uiState.errorMessage = e.message ?: "Something went wrong. Please try again."
        }
    }

    suspend fun updateUser() {
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

        if (uiState.name == "") {
            uiState.nameError = true
            uiState.errorMessage = "Name must be input."
            return
        }

        this.user?.name = uiState.name
        this.user?.email = uiState.email
        this.user?.manager = uiState.isManager
        this.user?.employee = uiState.isEmployee

        if (uiState.userType == CreateNewUserScreenState.UserTypes.MANAGER) {
            uiState.isManager = true
            uiState.isEmployee = false
        } else if (uiState.userType == CreateNewUserScreenState.UserTypes.EMPLOYEE) {
            uiState.isEmployee = true
            uiState.isManager = false
        }

        try {
            UserRepository.updateUser(this.user!!)
            uiState.signUpSuccess = true
        } catch (e: SignUpException) {
            uiState.errorMessage = e.message ?: "Something went wrong. Please try again."
        }
    }
}