package com.cs3450.dansfrappesraps.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun TrackerScreen(navHostController: NavHostController){

    Text(
        text = "CUSTOMER TRACKER",
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier
            .padding(8.dp)
    )

}