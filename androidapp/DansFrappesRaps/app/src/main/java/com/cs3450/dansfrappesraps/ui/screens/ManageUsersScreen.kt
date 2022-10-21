package com.cs3450.dansfrappesraps.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cs3450.dansfrappesraps.ui.components.Loader
import com.cs3450.dansfrappesraps.ui.components.UserListItem
import com.cs3450.dansfrappesraps.ui.viewmodels.ManageUsersViewModel
import com.cs3450.dansfrappesraps.ui.viewmodels.SortState
import com.cs3450.dansfrappesraps.util.userFilterText
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ManageUsersScreen(navHostController: NavHostController) {
    val viewModel: ManageUsersViewModel = viewModel()
    val state = viewModel.uiState
    val scope = rememberCoroutineScope()
    val sortedList by remember(state.sortState) {
        derivedStateOf {
            if (state.sortState == SortState.MANAGER) {
                state.users.filter { it.manager == true }
            } else if (state.sortState == SortState.EMPLOYEE) {
                state.users.filter { it.employee == true }
            } else if (state.sortState == SortState.CUSTOMER) {
                state.users.filter { it.employee == false && it.manager == false }
            } else {
                state.users
            }
        }
    }
    LaunchedEffect(true) {
        val loadingUsers = async { viewModel.getUsers() }
        delay(2000)
        loadingUsers.await()
        state.loading = false
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        if (state.loading) {
            Spacer(modifier = Modifier.height(16.dp))
            Loader()
        } else {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "Manage Users",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
            Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .wrapContentSize(Alignment.TopStart)
                        .clickable {
                            state.dropdownExpanded = !state.dropdownExpanded
                        }) {

                    Icon(
                        imageVector = Icons.Outlined.FilterList,
                        contentDescription = "Filter",
                        modifier = Modifier
                            .padding(8.dp)
                            .size(24.dp)
                    )
                    DropdownMenu(
                        expanded = state.dropdownExpanded,
                        onDismissRequest = { state.dropdownExpanded = false }) {
                        listOf(
                            SortState.ALL,
                            SortState.MANAGER,
                            SortState.EMPLOYEE,
                            SortState.CUSTOMER
                        ).forEach {
                            DropdownMenuItem(onClick = {
                                state.dropdownExpanded = false
                                state.sortState = it
                            }, text = { Text(text = it.name) })
                        }
                    }
                }
                Text(
                    text = userFilterText(state.sortState),
                    style = TextStyle(fontSize = MaterialTheme.typography.bodySmall.fontSize)
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(sortedList, key = { it.id!! }) { user ->
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