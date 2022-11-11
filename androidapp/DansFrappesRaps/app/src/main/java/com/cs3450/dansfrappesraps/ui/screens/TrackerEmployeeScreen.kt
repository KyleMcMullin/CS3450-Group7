package com.cs3450.dansfrappesraps.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cs3450.dansfrappesraps.ui.components.Loader
import com.cs3450.dansfrappesraps.ui.components.ManagePayrollItem
import com.cs3450.dansfrappesraps.ui.viewmodels.TrackerEmployeeViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TrackerEmployeeScreen(navHostController: NavHostController) {
    val viewModel: TrackerEmployeeViewModel = viewModel()
    val state = viewModel.uiState
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        val loadingUsers = async { viewModel.getUsers() }
        delay(2000)
        loadingUsers.await()
        state.loading = false
    }
    Column {
        if (state.loading) {
            Spacer(modifier = Modifier.height(16.dp))
            Loader()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    androidx.compose.material3.Text(
                        text = "Make Order",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { scope.launch { viewModel.approveAll() } }) {
                    androidx.compose.material3.Text(text = "Mark All Ingredients")
                }
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    items(state.users, key = { it.id!! }) { user ->
                        ManagePayrollItem(
                            user = user,
                            onApprove = {
                                scope.launch {
                                    viewModel.approveUser(user)
                                }
                            },
                            onSaveHours = {
                                scope.launch {
                                    viewModel.saveHours(user)
                                }
                            },
                            onEditRate = {
                                navHostController.navigate("editUser?id=${user.id}")
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}
