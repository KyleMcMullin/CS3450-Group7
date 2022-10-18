package com.cs3450.dansfrappesraps.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cs3450.dansfrappesraps.ui.viewmodels.CartScreenViewModel

@Composable
fun CartScreen(navHostController: NavHostController){
    var viewModel: CartScreenViewModel = viewModel()
    var scope = rememberCoroutineScope()
    var state = viewModel.uiState
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text = "My Cart",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(8.dp))
        Divider()
        Spacer(modifier = Modifier.size(16.dp))
        if(state.checkCart){
            Text(text = "Price:  $" + state.priceSum,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.size(8.dp))
            Row {
                //Check userBalance and compare to price
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Checkout")
                }
            }
        }
        else{
            Text(text =
            "Your cart is empty! Please check out our menu for coffee you can order!",
            style = MaterialTheme.typography.headlineSmall
            )
            Button(onClick = {navHostController.popBackStack()}) {
                Text("Back to menu")
            }
        }
    }
}