package com.cs3450.dansfrappesraps.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cs3450.dansfrappesraps.ui.components.DrinkItem
import com.cs3450.dansfrappesraps.ui.components.Loader
import com.cs3450.dansfrappesraps.ui.navigation.Routes
import com.cs3450.dansfrappesraps.ui.viewmodels.CartScreenViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToLong

@Composable
fun CartScreen(navHostController: NavHostController){
    var viewModel: CartScreenViewModel = viewModel()
    var scope = rememberCoroutineScope()
    var state = viewModel.uiState

    LaunchedEffect(true){
        scope.launch { viewModel.setupScreen() }
        state.loading = false
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        if (state.loading) {
            Spacer(modifier = Modifier.height(16.dp))
            Loader()
        } else {
            Text(
                text = "My Cart",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(8.dp)
                )
            }
            Divider()
            Spacer(modifier = Modifier.size(16.dp))
            if (state.frappuccinos.isNotEmpty()) {
                Button(onClick = {navHostController.navigate(Routes.app.route)}) {
                    Text(text = "Return to menu")
                }
                Spacer(modifier = Modifier.size(24.dp))
                Text(
                    text = "Price:  $" + state.priceSum.roundToLong(),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.size(8.dp))
                androidx.compose.material3.Text(
                    text = state.errorMessage,
                    style = TextStyle(color = MaterialTheme.colorScheme.error),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Left
                )
                Row {
                    //Check userBalance and compare to price
                    Button(onClick = {scope.launch{viewModel.checkout()}}) {
                        Text(text = "Checkout")
                    }
                }
                LazyColumn(){
                    items(state.frappuccinos) { drink ->
                        DrinkItem(
                            drink = drink,
                            onSelected = { navHostController.navigate("editMenu?id=${drink.id}") }
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            } else {
                Text(
                    text =
                    "\tYour cart is empty! Please check out our menu for more coffee",
                    style = MaterialTheme.typography.headlineSmall
                )
                Button(onClick = { navHostController.popBackStack() }) {
                    Text("Back to menu")
                }
            }
        }
    }