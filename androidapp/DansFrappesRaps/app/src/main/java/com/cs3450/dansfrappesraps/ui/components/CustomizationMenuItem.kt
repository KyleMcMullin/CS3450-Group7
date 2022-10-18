package com.cs3450.dansfrappesraps.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cs3450.dansfrappesraps.ui.models.Ingredient

//TODO
//Use for customizing a menu item
@Composable
fun CustomizationMenuItem(ingredient: Ingredient){
    Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)) {
        Row() {
            TODO()
        }
    }
}