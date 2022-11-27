package com.cs3450.dansfrappesraps.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cs3450.dansfrappesraps.ui.models.Drink
import com.cs3450.dansfrappesraps.ui.models.Ingredient


/*
TODO : Clean up this file into seperate files. Rename IngredientItem to IngredientQuantity
 */

@Composable
fun ManagerIngredientQuantity(
    ingredient: Ingredient,
    // on Plus pressed is going to be state.list.item.count++
    // on Minus pressed is going to be state.list.item.count--
    onPlusPressed: () -> Unit = {},
    onMinusPressed: () -> Unit = {}
) {
    Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth(.5F)) {
                ingredient.count?.let { QuantityItem(count = it, onPlusPressed, onMinusPressed) }
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                    Text(
                        text = ingredient.inventory?.name ?: "",
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    )
                }
            }
        }

    }
}

@Composable
fun DrinkQuantity(
    quantity: Int,
    onPlusPressed: () -> Unit = {},
    onMinusPressed: () -> Unit = {}
) {
//    ) {
    var displayNum by remember { mutableStateOf(quantity) }
    Card(elevation = CardDefaults.cardElevation(4.dp)) {
        QuantityItem(displayNum , {
            onPlusPressed()
            displayNum ++
        }) {
            onMinusPressed()
            if(displayNum>1) displayNum --
        }
    }
}



@Composable
fun CustomerIngredientQuantity(
    ingredient: Ingredient,
    // on Plus pressed is going to be state.list.item.count++
    // on Minus pressed is going to be state.list.item.count--
    onPlusPressed: () -> Unit = {},
    onMinusPressed: () -> Unit = {},
) {
    var displayNumb by remember { mutableStateOf(ingredient.count ?: 0) }
    Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth(.5F)) {
                QuantityItem(count = displayNumb, onPlusPressed = {
                    onPlusPressed()
                    displayNumb = ingredient.count ?: 0
                }, onMinusPressed = {
                    onMinusPressed()
                    displayNumb = ingredient.count ?: 0
                })
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                    Text(
                        text = ingredient.inventory?.name ?: "",
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    )
                }
            }
        }
    }
}