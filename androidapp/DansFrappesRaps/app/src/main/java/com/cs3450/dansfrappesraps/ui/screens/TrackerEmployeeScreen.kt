package com.cs3450.dansfrappesraps.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
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
import com.cs3450.dansfrappesraps.ui.navigation.Routes
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
        delay(2000)
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
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(16.dp),
                                    ) {
                    Row{
                                                Column{
                            Button(onClick = {
                                navHostController.navigate(
                                    Routes.trackerEmployee.route
                                )
                            }) {
                                Text("View Order 1")
                            }
                            Button(onClick = {
                                navHostController.navigate(
                                    Routes.trackerEmployee.route
                                )
                            }) {
                                Text("View Order 2")
                            }
                            Button(onClick = {
                                navHostController.navigate(
                                    Routes.trackerEmployee.route
                                )
                            }) {
                                Text("View Order 3")
                            }
                        }
                    }
                }
            }
        }
    }
}
