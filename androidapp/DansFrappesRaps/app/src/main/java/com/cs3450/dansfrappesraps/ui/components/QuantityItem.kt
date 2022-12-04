package com.cs3450.dansfrappesraps.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cs3450.dansfrappesraps.ui.theme.Shapes

@Composable
fun QuantityItem(
    count: Int, onPlusPressed: () -> Unit = {},
    onMinusPressed: () -> Unit = {}){
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedButton(
//                    shape = Shapes.small,
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
                        OutlinedButton(
//                            shape = Shapes.small,
                            onClick = {
                                onPlusPressed()
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "Plus")
                        }
                    }
            }