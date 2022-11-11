package com.cs3450.dansfrappesraps.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Size",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Left
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp), thickness = 3.dp
                )
                val radioOptions = listOf("Small","Medium","Large")
                val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .selectableGroup(), horizontalAlignment = Alignment.Start) {
                    radioOptions.forEach{ text ->
                        Row(verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.selectable(selected = (text ==selectedOption), onClick = {onOptionSelected(text)},
                            role = Role.RadioButton)) {
                            RadioButton(selected = (text == selectedOption),
                                modifier = Modifier
                                    .padding(3.dp), onClick = null)
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
                            .padding(3.dp), style = MaterialTheme.typography.headlineMedium
                    )
                    Divider()
                }


                                    LazyVerticalGrid(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp), columns = GridCells.Adaptive(minSize = 90.dp)
            ) {
                for (type in state.types) {
                    if (viewModel.hasIngredient(type = type)) {
                        item(span = {
                            GridItemSpan(maxLineSpan)
                        }) {
                            Text(
                                text = type,
                                modifier = Modifier.padding(10.dp)
                            )
                            Divider()
                        }
                    }

                        items(viewModel.getMatchType(type)) { j->
                                    var selected by remember { mutableStateOf(false) }
                                    FilterChip(
                                        selected = selected,
                                        onClick = { selected = !selected },
                                        label = {
                                            j.inventory?.name?.let { it1 ->
                                                Text(text = it1)
                                            }
                                        })
                            }
                    }
                }


            }

//                        LazyVerticalGrid(
//                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
//                            verticalArrangement = Arrangement.spacedBy(6.dp),
//                            horizontalArrangement = Arrangement.spacedBy(6.dp),
//                            modifier = Modifier
//                                .height(100.dp)
//                                .fillMaxWidth(),
//                            columns = GridCells.Adaptive(minSize = 90.dp),
//                            content = {
//                                items(viewModel.getMatchType(type = it)) { j ->
//                                    var selected by remember { mutableStateOf(false) }
//                                    FilterChip(
//                                        selected = selected,
//                                        onClick = { selected = !selected },
//                                        label = {
//                                            j.inventory?.name?.let { it1 ->
//                                                Text(text = it1)
//                                            }
//                                        })
//                                }
//                            })
                    }
                }
    }
//                        Spacer(modifier = Modifier.height(8.dp))