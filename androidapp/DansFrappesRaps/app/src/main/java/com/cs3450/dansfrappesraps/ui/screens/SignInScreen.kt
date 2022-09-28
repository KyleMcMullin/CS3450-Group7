package com.cs3450.dansfrappesraps.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.cs3450.dansfrappesraps.ui.components.SignButton
import com.cs3450.dansfrappesraps.ui.components.SignTextInput
import com.cs3450.dansfrappesraps.ui.navigation.Routes
import com.cs3450.dansfrappesraps.ui.viewmodels.SignInViewModel
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(navHostController: NavHostController) {
    val viewModel: SignInViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val state = viewModel.uiState
    LaunchedEffect(state.loginSuccess) {
        if (state.loginSuccess) {
            navHostController.navigate(Routes.app.route) {
                popUpTo(0)
            }
        }
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
                text = "Dan's Frappes & Raps",
                style = MaterialTheme.typography.h3,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(25.dp))
            Text(
                text = state.errorMessage,
                style = TextStyle(color = MaterialTheme.colors.error),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Right
            )
            SignTextInput(
                value = state.email,
                onValueChange = { state.email = it },
                placeholder = { Text("Email") },
                error = state.emailError
            )
            SignTextInput(
                value = state.password,
                onValueChange = { state.password = it },
                placeholder = { Text("Password") },
                error = state.passwordError,
                password = true
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                SignButton(onClick = { scope.launch { viewModel.signIn() } }, text = "Sign In")
            }
            Spacer(modifier = Modifier.padding(3.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "No account yet? ",
                    textAlign = TextAlign.Right,
                    style = MaterialTheme.typography.subtitle2
                )
                Text(
                    modifier = Modifier.clickable { navHostController.navigate(Routes.signUp.route) },
                    text = "Register here.",
                    style = MaterialTheme.typography.subtitle2,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}