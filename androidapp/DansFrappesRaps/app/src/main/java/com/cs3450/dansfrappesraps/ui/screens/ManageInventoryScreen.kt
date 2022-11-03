package com.cs3450.dansfrappesraps.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.FilterListOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cs3450.dansfrappesraps.ui.components.InventoryItem
import com.cs3450.dansfrappesraps.ui.components.Loader
import com.cs3450.dansfrappesraps.ui.viewmodels.ManageInventoryViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ManageInventoryScreen(navHostController: NavHostController) {
    val viewModel: ManageInventoryViewModel = viewModel()
    val state = viewModel.uiState
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        val loadingInventory = async { viewModel.getInventory() }
        delay(2000)
        loadingInventory.await()
        state.loading = false
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (state.loading) {
            Spacer(modifier = Modifier.height(16.dp))
            Loader()
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Manage Inventory",
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(.9F)
                        )
                    }
                    Column(modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        var icon by remember { mutableStateOf(false) }
                        var dropdown by remember { mutableStateOf(false) }
                        Icon(imageVector = (if (icon) {
                            Icons.Filled.FilterListOff
                        } else {
                            Icons.Filled.FilterList
                        }), contentDescription = "Sort",
                            modifier = Modifier.clickable {
                                dropdown = !dropdown
                                icon = !icon
                                state.isAllType = !state.isAllType
                            })
                        DropdownMenu(
                            expanded = dropdown,
                            onDismissRequest = { dropdown = false }) {
                            state.types.forEach {
                                DropdownMenuItem(onClick = {
                                    state.type = it
                                    dropdown = false
                                }, text = { Text(it) })
                            }
                        }
                    }
                }

                    Column(modifier = Modifier.fillMaxSize()) {
                        Box(modifier = Modifier.fillMaxSize()) {

                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                items(state.inventory, key = { it.id ?: "" }) { inventory ->
                                    if(inventory.type==state.type || state.isAllType) {
                                        InventoryItem(
                                            inventory = inventory,
                                            onEditPressed = {
                                                navHostController.navigate("editInventory?id=${inventory.id}")
                                            },
                                            onDeletePressed = {
                                                scope.launch {
                                                    viewModel.deleteInventory(inventory)
                                                }
                                            }
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }