package com.cs3450.dansfrappesraps.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cs3450.dansfrappesraps.ui.components.DrinkItem
import com.cs3450.dansfrappesraps.ui.navigation.Routes
import com.cs3450.dansfrappesraps.ui.viewmodels.TrackerScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun TrackerScreen(navHostController: NavHostController){
    var viewModel: TrackerScreenViewModel = viewModel()
    var scope = rememberCoroutineScope()
    var state = viewModel.uiState

    Text(
        text = "Order Tracker",
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier
            .padding(8.dp)
    )

    Divider()
    Spacer(modifier = Modifier.size(16.dp))
    if (state.frappuccinos.isNotEmpty()) {
        Button(onClick = {navHostController.navigate(Routes.app.route)}) {
            Text(text = "Return to menu")
        }
        Button(onClick = {navHostController.navigate(Routes.tracker.route)}) {
            Text(text = "Track Orders")
        }
        Spacer(modifier = Modifier.size(24.dp))

        Spacer(modifier = Modifier.size(8.dp))
        androidx.compose.material3.Text(
            text = state.errorMessage,
            style = TextStyle(color = MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Left
        )

    } else {
        Text(
            text =
            "\tYour cart is empty! Please check out our menu for more coffee",
            style = MaterialTheme.typography.headlineSmall
        )
        Button(onClick = { navHostController.popBackStack() }) {
            Text("Back to menu")
        }
        Button(onClick = {navHostController.navigate(Routes.tracker.route)}) {
            Text(text = "Track Orders")
        }
    }
}