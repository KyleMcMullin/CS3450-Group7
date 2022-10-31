package com.cs3450.dansfrappesraps.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.cs3450.dansfrappesraps.ui.models.Inventory

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InventoryItem(
    inventory: Inventory,
    onEditPressed: () -> Unit = {},
    onDeletePressed: () -> Unit = {},
) {
    val swipeableState = rememberSwipeableState(initialValue = SwipeState.CLOSED)
    val anchors = mapOf(
        0f to SwipeState.CLOSED,
        -200f to SwipeState.OPEN
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                orientation = Orientation.Horizontal
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = onDeletePressed,
                modifier = Modifier
                    .fillMaxWidth(.5f),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
        Surface(
            modifier = Modifier
                .offset { IntOffset(swipeableState.offset.value.toInt(), 0) }
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(4.dp)
                )
                .clickable { onEditPressed() },
            elevation = 2.dp,
            shape = RoundedCornerShape(4.dp),
        ) {
            Column {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Text(
                                text = inventory.name ?: "",
                                modifier = Modifier.padding(10.dp),
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                        Divider(modifier = Modifier.padding(4.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Text(text = " Quantity: ${inventory.quantity ?: ""}", modifier = Modifier.padding(10.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "PPU: $${inventory.PPU ?: ""}",
                                modifier = Modifier.padding(10.dp),
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Type: ${inventory.type ?: ""}", modifier = Modifier.padding(10.dp))
                        }
                    }
                }
            }
        }
    }
}