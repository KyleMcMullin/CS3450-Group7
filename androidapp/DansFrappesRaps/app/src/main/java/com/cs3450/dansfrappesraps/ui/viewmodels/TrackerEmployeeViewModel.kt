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
}