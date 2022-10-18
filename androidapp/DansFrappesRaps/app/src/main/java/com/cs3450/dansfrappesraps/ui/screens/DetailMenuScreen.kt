package com.cs3450.dansfrappesraps.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cs3450.dansfrappesraps.ui.components.IngredientItem
import com.cs3450.dansfrappesraps.ui.components.Loader
import com.cs3450.dansfrappesraps.ui.viewmodels.EditMenuViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

@Composable
fun DetailMenuScreen(navController: NavController, id: String?) {
    var viewModel: EditMenuViewModel = viewModel()
    var scope = rememberCoroutineScope()
    var state = viewModel.uiState

    LaunchedEffect(true) {
        val loadingIngredients = async { viewModel.getIngredients() }
        delay(2000)
        loadingIngredients.await()
        viewModel.setUpInitialState(id)
        state.loading = false
    }
    if (state.loading) {
        Spacer(modifier = Modifier.height(16.dp))
        Loader()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Drink name", style = MaterialTheme.typography.headlineLarge)
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp), thickness = 3.dp
            )
            Text(modifier = Modifier.fillMaxWidth(), text = "Size",  style = MaterialTheme.typography.headlineMedium, textAlign = TextAlign.Left)
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp), thickness = 3.dp
            )
            Row() {
                Column(modifier = Modifier.fillMaxWidth(.5F)) {
                    Button(modifier = Modifier
                        .fillMaxWidth()
                        .padding(3.dp), onClick = {}) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Small",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    Button(modifier = Modifier
                        .fillMaxWidth()
                        .padding(3.dp), onClick = {}) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Medium",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }

                }
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp), onClick = {}){
                    Text(modifier = Modifier.fillMaxWidth(),
                        text = "Large",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)) {
                Text(text = "Customization", modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp), style = MaterialTheme.typography.headlineMedium)
                Divider()
//                Text(text = "Quantity", modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.titleMedium,)
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.9F)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(state.ingredients, key = { it.inventory?.id!! }) { ingredient ->
                    IngredientItem(
                        ingredient = ingredient,
                        onMinusPressed = {},
                        onPlusPressed = {},
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

            }
//            Button(modifier = Modifier.fillMaxWidth(), onClick = { /*TODO*/ }) {

            }
        }
    }