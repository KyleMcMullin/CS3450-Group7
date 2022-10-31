package com.cs3450.dansfrappesraps.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
<<<<<<< HEAD
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
=======
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
>>>>>>> e4ca66f (clean up all code and dynamic spacing for filterchips)
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cs3450.dansfrappesraps.ui.components.Loader
import com.cs3450.dansfrappesraps.ui.models.Ingredient
import com.cs3450.dansfrappesraps.ui.viewmodels.DetailMenuViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMenuScreen(navController: NavController, id: String?) {
    var viewModel: DetailMenuViewModel = viewModel()
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
            Text(text = state.drinkName, style = MaterialTheme.typography.headlineLarge)
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp), thickness = 3.dp
            )
            ExtendedFloatingActionButton(onClick = { scope.launch { viewModel.addToCart(id) } }, modifier = Modifier.align(Alignment.End)) {
                Text(text = "Add to Cart")
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp), thickness = 3.dp
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Size",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Left
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp), thickness = 3.dp
            )
            Row {
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
                    .padding(3.dp), onClick = {}) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Large",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                Text(
                    text = "Customization", modifier = Modifier
                        .fillMaxWidth()
                        .padding(3.dp), style = MaterialTheme.typography.headlineMedium
                )
                Divider()
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(state.types, key = { it }) {
                    var showDetail by remember { mutableStateOf(false) }

                    Surface(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(4.dp)
                            )
                            .clickable { showDetail = !showDetail },
                        tonalElevation = 2.dp,
                        shape = RoundedCornerShape(4.dp),
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(4.dp)
                                    ) {
                                        Text(
                                            text = it,
                                            modifier = Modifier.padding(10.dp)
                                        )
                                    }
                                    Column {

                                        AnimatedVisibility(
                                            visible = showDetail,
                                            enter = expandVertically(),
                                            exit = shrinkVertically()
                                        ) {
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                Divider(
                                                    modifier = Modifier.padding(4.dp)
                                                )
                                                LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 5.dp), content = {
                                                    for(chip in state.customization){
                                                    item{
                                                        var selected by remember { mutableStateOf(false) }
                                                    if (chip.inventory?.type == it) {
                                                        FilterChip(
                                                            selected = selected,
                                                            onClick = { selected = !selected }, label = { chip.inventory!!.name?.let { it1 -> Text(text = it1) } })
                                                    }
                                                } }
                                                })
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}