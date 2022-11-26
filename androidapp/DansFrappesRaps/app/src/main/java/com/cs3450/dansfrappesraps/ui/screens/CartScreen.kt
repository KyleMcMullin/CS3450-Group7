package com.cs3450.dansfrappesraps.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.ShoppingCartCheckout
import androidx.compose.material3.*
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
import com.cs3450.dansfrappesraps.ui.components.CartDrinkItem
import com.cs3450.dansfrappesraps.ui.components.DrinkItem
import com.cs3450.dansfrappesraps.ui.components.Loader
import com.cs3450.dansfrappesraps.ui.navigation.Routes
import com.cs3450.dansfrappesraps.ui.viewmodels.CartScreenViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navHostController: NavHostController) {
    var viewModel: CartScreenViewModel = viewModel()
    var scope = rememberCoroutineScope()
    var state = viewModel.uiState

    LaunchedEffect(true) {
        state.loading = true
        val setup = async { viewModel.setupScreen() }
        delay(2000)
        setup.await()
        state.loading = false
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if (state.loading) {
            Spacer(modifier = Modifier.height(16.dp))
            Loader()
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                Text(
                    text = "My Cart",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .padding(8.dp)
                )
                Divider()
                Spacer(modifier = Modifier.size(16.dp))
                }
                    if (state.frappuccinos.isNotEmpty()) {
                        Scaffold(floatingActionButton = {
                            ExtendedFloatingActionButton(
                                onClick = { scope.launch { viewModel.checkout() } } ,
                                contentColor = MaterialTheme.colorScheme.primary
                            ) {
                                Icon(Icons.Outlined.ShoppingCartCheckout, contentDescription = "Cart Checkout")
                                Text(text = "Checkout")
                            }
                        }, content = { padding ->
                            Column(modifier= Modifier
                                .fillMaxSize()
                                .padding(padding)) {
                                Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                                    Text(text = "Frappuccinos ordered: " + state.drinkCount)
                                    Spacer(modifier = Modifier.size(8.dp))
                                    Text(
                                        text = "Price:  $" + state.priceSum.toFloat(),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Spacer(modifier = Modifier.size(8.dp))
                                    Text(
                                        text = state.errorMessage,
                                        style = TextStyle(color = MaterialTheme.colorScheme.error),
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Left
                                    )
                                    LazyColumn() {
                                        items(state.frappuccinos) { drink ->
                                            DrinkItem(
                                                drink = drink,
                                                onSelected = {
                                                    navHostController.navigate(
                                                        "detailMenu?id=${null}&index=${
                                                            state.frappuccinos.indexOf(
                                                                drink
                                                            )
                                                        }"
                                                    )
                                                },
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                        }
                                    }
                                }
                            }
                        })
                        } else {
                            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                                Text(
                                    text =
                                    "\tYour cart is empty! Please check out our menu for more coffee.",
                                    style = MaterialTheme.typography.headlineSmall
                                )
                                Button(onClick = {
                                    navHostController.navigate(Routes.tracker.route)
                                }) {
                                    Text("Track Order")
                                }
                            }
                        }
        }
    }
    }
}