package com.cs3450.dansfrappesraps.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SignButton(
    text: String,
    onClick: () -> Unit = {}
){
    Button(onClick = onClick, modifier = Modifier.fillMaxWidth().height(50.dp)){
        Text(text = text)
    }
}