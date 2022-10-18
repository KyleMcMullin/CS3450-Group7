package com.cs3450.dansfrappesraps.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cs3450.dansfrappesraps.ui.components.DrinkItem
import com.cs3450.dansfrappesraps.ui.components.Loader
import com.cs3450.dansfrappesraps.ui.viewmodels.ManageMenuViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

@Composable
fun MenuScreen(navHostController: NavHostController) {
    val viewModel: ManageMenuViewModel = viewModel()
    val state = viewModel.uiState
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        val loadingDrinks = async { viewModel.getDrinks() }
        delay(2000)
        loadingDrinks.await()
        state.loading = false
    }
    Column {
        if (state.loading) {
            Spacer(modifier = Modifier.height(16.dp))
            Loader()
        }
        else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    Text(
                        text = "Menu",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn {
                    items(state.drinks) { drink ->
                        DrinkItem(
                            drink = drink,
                            onSelected = { navHostController.navigate("detailMenu?id=${drink.id}") })
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}
