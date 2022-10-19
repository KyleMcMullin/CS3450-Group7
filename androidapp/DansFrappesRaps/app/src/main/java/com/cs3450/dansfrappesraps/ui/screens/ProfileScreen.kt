package com.cs3450.dansfrappesraps.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cs3450.dansfrappesraps.ui.components.LabelledTextInput
import com.cs3450.dansfrappesraps.ui.viewmodels.ProfileScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(navHostController: NavHostController) {
    val viewModel: ProfileScreenViewModel = viewModel()
    var state = viewModel.uiState
    var scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        viewModel.getUser()
    }
    Column(horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth()) {
        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {  },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Edit Profile",
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(8.dp)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Text(
                text = "Name: ${state.name}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(8.dp)
            )
            if (state.isManager) {
                Text(
                    text = "Manager",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
            if (state.isEmployee) {
                Text(
                    text = "Employee",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Text(
                text = "Email: ${state.email}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(8.dp)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Text(
                text = "Current Balance: ${state.balance}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(8.dp)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            AnimatedVisibility(
                visible = state.isExpanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Divider(modifier = Modifier.padding(4.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        LabelledTextInput(
                            placeholder = { Text(text = "Add Balance") },
                            value = state.addBalance,
                            onValueChange = {
                                state.addBalance = it
                            },
                            label = "Add Balance",
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                }
            }
        }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (state.isEmployee || state.isManager) {
                    Button(onClick = {}) {
                    }
                    Text("Withdraw Balance")
                } else {
                    Button(onClick = {
                        if (state.isExpanded) {
                            scope.launch { viewModel.updateBalance() }
                        } else {
                            state.isExpanded = true
                        }
                    }) {
                        Text("Add Balance")
                    }

                }
            }
    }
}