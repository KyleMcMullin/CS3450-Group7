package com.cs3450.dansfrappesraps.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cs3450.dansfrappesraps.ui.components.LabelledTextInput
import com.cs3450.dansfrappesraps.ui.components.Loader
import com.cs3450.dansfrappesraps.ui.viewmodels.EmployeePayrollViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun EmployeePayrollScreen(navController: NavController) {
    val viewModel: EmployeePayrollViewModel = viewModel()
    val state = viewModel.uiState
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        val loadingUser = async {viewModel.getUser()}
        delay(2000)
        loadingUser.await()
        state.loading = false
    }
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
                Text(
                    text = "Payroll",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Name: ${state.name}")
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Text(text = "Pay Rate: $${state.payRate}")
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "Current Balance: $${state.balance}")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Divider(modifier = Modifier.padding(4.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        LabelledTextInput(
                            placeholder = { Text(text="Hours") },
                            value = state.hours,
                            onValueChange = {
                                state.hours = it
                            },
                            label = "Hours",
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                    if (state.hoursError) {
                        Text(text = state.errorMessage, color = MaterialTheme.colorScheme.error)
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                        Button(onClick = {
                            state.saved = true
                            scope.launch {
                                delay(500)
                                viewModel.updateHours()
                            }
                        }) {
                            if (state.saved) {
                                Text(text = "Saved")
                            } else {
                                Text(text = "Save Hours")
                            }
                        }
                    }
                }
            }
        }
    }
}