package com.cs3450.dansfrappesraps.ui.screens

import androidx.compose.material3.Checkbox
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cs3450.dansfrappesraps.ui.components.SignButton
import com.cs3450.dansfrappesraps.ui.components.LabelledTextInput
import com.cs3450.dansfrappesraps.ui.navigation.Routes
import com.cs3450.dansfrappesraps.ui.viewmodels.AdjustInventoryViewModel
import kotlinx.coroutines.launch


@Composable
fun AdjustInventoryScreen(navHostController: NavHostController, id: String?) {
    val viewModel: AdjustInventoryViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val state = viewModel.uiState
    LaunchedEffect(state.adjustSuccess) {
        if (state.adjustSuccess) {
            navHostController.navigate(Routes.manageInventory.route) {
                popUpTo(Routes.manageInventory.route) {
                    inclusive = true
                }
            }
        }
    }
    LaunchedEffect(true) {
        viewModel.setUpInitialState(id)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                if (id == null || id == "new") {
                    Text(
                        text = "Create New Item",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )
                } else {
                    Text(
                        text = "Edit Item",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )
                }
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = state.errorMessage,
                style = TextStyle(color = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Left
            )
            LabelledTextInput(
                value = state.name,
                label = "Name",
                onValueChange = { state.name = it },
                placeholder = { Text("Item Name") },
                error = state.nameError
            )
            LabelledTextInput(
                value = state.quantity,
                label = "Quantity",
                onValueChange = { state.quantity = it },
                placeholder = { Text("Item Quantity") },
                error = state.quantityError,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            Checkbox(
                checked = state.isCountable,
                onCheckedChange = { state.isCountable = it }
            )
            LabelledTextInput(
                value = state.PPU,
                label = "PPU",
                onValueChange = { state.PPU = it },
                placeholder = { Text("Price Per Unit") },
                error = state.PPUError,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.AttachMoney,
                        contentDescription = "Dollar"
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal)
            )
            Box() {
                LabelledTextInput(
                    value = state.type,
                    label = "Type",
                    onValueChange = { state.type = it },
                    placeholder = { Text("Item Type") },
                    error = state.typeError,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.ArrowDropDown,
                            contentDescription = "Dropdown",
                            modifier = Modifier.clickable {
                                state.dropDownExpanded = true
                            }
                        )
                    },
                )

                DropdownMenu(
                    expanded = state.dropDownExpanded,
                    onDismissRequest = { state.dropDownExpanded = false }) {
                    state.types.forEach {
                        DropdownMenuItem(onClick = {
                            state.type = it
                            state.dropDownExpanded = false
                        }, text = { Text(it) })
                    }
                }
            }
            Spacer(modifier = Modifier.padding(6.dp))
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (id == null || id == "new") {
                    SignButton(
                        text = "Create Item",
                        onClick = {
                            scope.launch {
                                viewModel.addInventory()
                            }
                        }
                    )
                } else {
                    SignButton(
                        text = "Save Item",
                        onClick = {
                            scope.launch {
                                viewModel.adjustInventory(id)
                            }
                        }
                    )
                }
            }
        }
    }
}