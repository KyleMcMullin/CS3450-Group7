package com.cs3450.dansfrappesraps.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cs3450.dansfrappesraps.ui.components.IngredientItem
import com.cs3450.dansfrappesraps.ui.components.Loader
import com.cs3450.dansfrappesraps.ui.models.Ingredient
import com.cs3450.dansfrappesraps.ui.navigation.Routes
import com.cs3450.dansfrappesraps.ui.viewmodels.DetailMenuViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMenuScreen(navController: NavController, id: String, index: String) {
    var viewModel: DetailMenuViewModel = viewModel()
    var scope = rememberCoroutineScope()
    var state = viewModel.uiState

    LaunchedEffect(true) {
        val loadingIngredients = async { viewModel.getIngredients() }
        delay(2000)
        loadingIngredients.await()
        viewModel.setUpInitialState(id, index)
        state.loading = false
    }
    if (state.loading) {
        Spacer(modifier = Modifier.height(16.dp))
        Loader()
    } else {
        Scaffold(floatingActionButton = {ExtendedFloatingActionButton(
            onClick = {
                viewModel.addToCart(null)
                navController.navigate(Routes.cart.route)
            },

            contentColor = androidx.compose.material3.MaterialTheme.colorScheme.primary){
            Icon(Icons.Outlined.ShoppingCart, contentDescription = "Cart")
            androidx.compose.material3.Text(text = "Add to Cart")
        }}, content = { padding->
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(tonalElevation = 2.dp,
                shape = RoundedCornerShape(20.dp),) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = state.drinkName,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp),
                text = "Size",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Left
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp), thickness = 3.dp
            )
            val radioOptions = listOf("Small", "Medium", "Large")
            val openDialog = remember { mutableStateOf(false) }
            var ingredient by remember{ mutableStateOf(Ingredient()) }
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectableGroup(), horizontalAlignment = Alignment.Start
            ) {
                radioOptions.forEach { text ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .selectable(
                                selected = (text == selectedOption),
                                onClick = { onOptionSelected(text) },
                                role = Role.RadioButton
                            )
                    ) {
                        RadioButton(
                            selected = (text == selectedOption),
                            modifier = Modifier
                                .padding(3.dp), onClick = null
                        )
                        Text(
                            modifier = Modifier,
                            text = text,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

            }
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                ) {
                    Text(
                        text = "Customization", modifier = Modifier
                            .fillMaxWidth()
                            .padding(3.dp), style = MaterialTheme.typography.headlineSmall
                    )
                }
                Surface(
                    tonalElevation = 1.dp,
                    shape = RoundedCornerShape(20.dp),
                ) {
                    LazyVerticalGrid(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxSize(),
                        columns = GridCells.Adaptive(minSize = 90.dp)
                    ) {
                        for (type in state.types) {
                            if (viewModel.hasIngredient(type = type)) {
                                item(span = {
                                    GridItemSpan(maxLineSpan)
                                }) {
                                    Text(
                                        text = type,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Divider()
                                }
                            }

                            items(viewModel.getMatchType(type)) { j ->
                                var selected by remember { mutableStateOf(false) }
                                FilterChip(
                                    selected = selected,
                                    onClick = {
                                        selected = !selected
                                        if(j.inventory?.isCountable == true){
                                            openDialog.value = true
                                            ingredient = j
                                        }
                                    },
                                    label = {
                                        j.inventory?.name?.let { it1 ->
                                            Text(
                                                text = it1,
                                                modifier = Modifier.fillMaxWidth(),
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    })

                                if (openDialog.value) {
                                    val tempCount = ingredient.count
                                    AlertDialog(onDismissRequest = { openDialog.value = false
                                        selected=false },
                                        title = { IngredientItem(
                                            ingredient = ingredient,
                                            onMinusPressed = {
                                                 viewModel.decrementIngredient(ingredient)
                                            },
                                            onPlusPressed = {
                                                viewModel.incrementIngredient(ingredient)
                                            },
                                            isForToast = true
                                        ) }, dismissButton = {
                                            TextButton(
                                                onClick = {
                                                    openDialog.value = false
                                                    ingredient.count = tempCount
                                                }
                                            ) {
                                                Text("Dismiss")
                                            }
                                        }, confirmButton = {
                                            TextButton(
                                                onClick = {
                                                    openDialog.value = false
                                                }
                                            ) {
                                                Text("Confirm")
                                            }
                                        })
                                }
                            }
                        }
                    }
                }
            }
        }
    })
    }
}