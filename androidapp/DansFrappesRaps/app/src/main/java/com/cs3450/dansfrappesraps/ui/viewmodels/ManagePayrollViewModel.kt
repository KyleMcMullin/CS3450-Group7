package com.cs3450.dansfrappesraps.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.cs3450.dansfrappesraps.ui.models.User
import com.cs3450.dansfrappesraps.ui.repositories.UserRepository

class ManagePayrollScreenState {
    var _users = mutableListOf<User>()
    val users: List<User> get() = _users
    var loading by mutableStateOf(false)
}

class ManagePayrollViewModel(application: Application): AndroidViewModel(application) {
    val uiState = ManagePayrollScreenState()

    suspend fun getUsers() {
        uiState.loading = true
        uiState._users.clear()
        uiState._users.addAll(UserRepository.getAllUsers())
        uiState._users.removeIf { user -> user.employee == false }
    }

}