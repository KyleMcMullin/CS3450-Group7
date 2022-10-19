package com.cs3450.dansfrappesraps.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cs3450.dansfrappesraps.ui.components.SignButton
import com.cs3450.dansfrappesraps.ui.components.SignTextInput
import com.cs3450.dansfrappesraps.ui.navigation.Routes
import com.cs3450.dansfrappesraps.ui.viewmodels.EditUserProfileViewModel
import kotlinx.coroutines.launch

@Composable
fun EditUserProfileScreen(navHostController: NavHostController) {
    var viewModel: EditUserProfileViewModel = viewModel()
    var state = viewModel.uiState
    var scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        viewModel.getUser()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Update User Info",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(25.dp))
            Text(
                text = state.errorMessage,
                style = TextStyle(color = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Left
            )
            SignTextInput(
                value = state.name,
                onValueChange = {state.name = it},
                placeholder = { Text("Name")},
                error = state.nameError
            )
            SignTextInput(
                value = state.email,
                onValueChange = { state.email = it },
                placeholder = { Text("Email") },
                error = state.emailError
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                SignButton(onClick = {
                    navHostController.popBackStack()
                    scope.launch { viewModel.updateUser() }
                }, text = "Update User")
            }
        }
    }
}