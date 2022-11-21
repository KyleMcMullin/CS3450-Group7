package com.cs3450.dansfrappesraps.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun QuantityItem(
    count: Int, onPlusPressed: () -> Unit = {},
    onMinusPressed: () -> Unit = {}){
            Row {
                Button(
                    onClick = {
                        onMinusPressed()
                    }
                        ) {
                        Icon(imageVector = Icons.Default.Remove, contentDescription = "Minus")
                    }
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            modifier = Modifier.padding(horizontal = 3.dp),
                            text = "$count",
                            style = MaterialTheme.typography.headlineMedium,
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Button(
                            onClick = {
                                onPlusPressed()
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "Plus")
                        }
                    }
            }