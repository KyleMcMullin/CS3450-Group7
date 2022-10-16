package com.cs3450.dansfrappesraps.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import com.cs3450.dansfrappesraps.ui.models.Ingredient

@Composable
fun IngredientItem(
    ingredient: Ingredient,
    // on Plus pressed is going to be state.list.item.count++
    // on Minus pressed is going to be state.list.item.count--
    onPlusPressed: () -> Unit = {},
    onMinusPressed: () -> Unit = {}
) {
    Row() {
        Button(onClick = onMinusPressed) {
            Icon(imageVector = Icons.Default.Remove, contentDescription = "Minus")
        }
        Text(text = "${ingredient.count}")
        Button(onClick= onPlusPressed) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Plus")
        }
        Text(text = ingredient.inventory?.name ?: "")

    }

}