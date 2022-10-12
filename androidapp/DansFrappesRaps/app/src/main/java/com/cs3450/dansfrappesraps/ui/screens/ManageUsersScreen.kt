package com.cs3450.dansfrappesraps.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cs3450.dansfrappesraps.ui.components.UserListItem
import com.cs3450.dansfrappesraps.ui.viewmodels.ManageUsersScreenState
import com.cs3450.dansfrappesraps.ui.viewmodels.ManageUsersViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ManageUsersScreen(navHostController: NavHostController) {
    val viewModel: ManageUsersViewModel = viewModel()
    val state = viewModel.uiState
    val scope = rememberCoroutineScope()
    val sortedList = state.users
//    val sortedList by remember(state.sortList) {
//        derivedStateOf {
//            if (state.sortList == ManageUsersScreenState.SORT_BY_MANAGER) {
//                state.users.sortedBy { it.manager }
//            } else if (state.sortList == "employee") {
//                state.users.sortedBy { it.employee }
//            } else {
//                state.users
//            }
//        }
//    }
    LaunchedEffect(true) {
        val loadingUsers = async {viewModel.getUsers()}
        delay(2000)
        loadingUsers.await()
        state.loading = false
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.loading) {
            Spacer(modifier = Modifier.height(16.dp))
            //Loader()
        } else {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = state.sortManagersOnly, onCheckedChange = {state.sortManagersOnly = it})
                Text(text = "Managers Only")
                Spacer(modifier = Modifier.width(16.dp))
                Checkbox(checked = state.sortEmployeesOnly, onCheckedChange = {state.sortEmployeesOnly = it})
                Text(text = "Employees Only")
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                items(state.users, key = {it.id!!}) { user ->
                    UserListItem(
                        user = user,
                        onEditPressed = {
                            //TODO navigate to edit user screen
                        navHostController.navigate("editUser?id=${user.id}")
                    }, onDeletePressed = {
                        scope.launch {
                            viewModel.deleteUser(user)
                        }
                    })
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}