package com.cs3450.dansfrappesraps.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.cs3450.dansfrappesraps.ui.models.User
import com.cs3450.dansfrappesraps.ui.repositories.UserRepository

enum class SortState {
    ALL,
    CUSTOMER,
    EMPLOYEE,
    MANAGER
}
class ManageUsersScreenState {
    val _users = mutableStateListOf<User>()
    val users: List<User> get() = _users
    var loading by mutableStateOf(false)
    var sortState by mutableStateOf(SortState.ALL)
    var dropdownExpanded by mutableStateOf(false)
}

class ManageUsersViewModel(application: Application): AndroidViewModel(application) {
    val uiState = ManageUsersScreenState()

    suspend fun getUsers() {
        uiState.loading = true
        uiState._users.clear()
        uiState._users.addAll(UserRepository.getAllUsers())
    }

    suspend fun deleteUser(user: User) {
        UserRepository.deleteUser(user)
        uiState._users.remove(user)
    }
}