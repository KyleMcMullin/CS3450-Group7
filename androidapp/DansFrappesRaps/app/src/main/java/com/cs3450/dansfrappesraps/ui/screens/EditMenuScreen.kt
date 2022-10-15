package com.cs3450.dansfrappesraps.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cs3450.dansfrappesraps.ui.components.LabelledTextInput
import com.cs3450.dansfrappesraps.ui.components.SignTextInput
import com.cs3450.dansfrappesraps.ui.viewmodels.EditMenuViewModel

@Composable
fun EditMenuScreen(navController: NavController, id: String?) {
    var viewModel: EditMenuViewModel = viewModel()
    var scope = rememberCoroutineScope()
    var state = viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LabelledTextInput(
            value = state.name,
            label = "Drink Name",
            onValueChange = {  },
            placeholder = { Text("Drink Name") },
            error = false
        )
    }
}