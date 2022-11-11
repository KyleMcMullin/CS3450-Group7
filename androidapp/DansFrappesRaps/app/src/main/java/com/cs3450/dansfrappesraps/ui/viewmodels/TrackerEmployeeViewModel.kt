package com.cs3450.dansfrappesraps.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.cs3450.dansfrappesraps.ui.models.User
import com.cs3450.dansfrappesraps.ui.repositories.UserRepository

class TrackerEmployeeScreenState {
    var _users = mutableStateListOf<User>()
    val users: List<User> get() = _users
    var loading by mutableStateOf(false)
}

class TrackerEmployeeViewModel(application: Application): AndroidViewModel(application) {
    val uiState = TrackerEmployeeScreenState()

    suspend fun getUsers() {
        uiState.loading = true
        uiState._users.clear()
        uiState._users.addAll(UserRepository.getAllUsers())
        uiState._users.removeIf { user -> user.employee == false }
    }

    suspend fun approveAll() {
        uiState._users.forEach { user ->
            user.balance = user.balance?.plus((user.payRate?.times(user.hours.toDouble())!!))
            user.hours = 0
            UserRepository.updateUser(user)
        }
        uiState._users.clear()
        uiState._users.addAll(UserRepository.getAllUsers())
        uiState._users.removeIf { user -> user.employee == false }
    }

    suspend fun approveUser(user: User) {
        user.balance = user.balance?.plus((user.payRate?.times(user.hours.toDouble())!!))
        user.hours = 0
        uiState._users.remove(user)
        uiState._users.add(user)
        UserRepository.updateUser(user)

    }

    suspend fun saveHours(user: User) {
        UserRepository.updateUser(user)
        val index = uiState._users.indexOf(user)
        uiState._users.remove(user)
        uiState._users.add(index, user)
    }

}