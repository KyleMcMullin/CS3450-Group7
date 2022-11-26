package com.cs3450.dansfrappesraps.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cs3450.dansfrappesraps.R
import com.cs3450.dansfrappesraps.ui.models.Drink


//Manager DrinkItem
@OptIn(ExperimentalMaterialApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun DrinkItem(
    drink: Drink,
    onSelected: () -> Unit,
    onDeletePressed: () -> Unit,
    quantity: Int,
){
    val swipeableState = rememberSwipeableState(initialValue = SwipeState.CLOSED)
    val anchors = mapOf(
        0f to SwipeState.CLOSED,
        -200f to SwipeState.OPEN
    )
    Card(
        shape = RoundedCornerShape(20.dp),
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
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                )
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
        Card(
            modifier = Modifier
                .offset { IntOffset(swipeableState.offset.value.toInt(), 0) }
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(4.dp)
                )
                .clickable { onSelected() },
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape(4.dp),
        ) {
            Column {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Column( verticalArrangement = Arrangement.Center) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Text(
                                text = drink.name ?: "",
                                modifier = Modifier.padding(10.dp),
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                        Divider(modifier = Modifier.padding(4.dp))

                        GlideImage(
                            model = Uri.parse(drink.image ?: ""),
                            contentDescription = "Drink Image",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                        )

                        Divider(modifier = Modifier.padding(4.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(4.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            var appendString = ""
                            for (ingredient in drink.ingredients!!) {
                                appendString += ingredient.inventory?.name
                                appendString += if (drink.ingredients.lastIndex == drink.ingredients.indexOf(ingredient)) {
                                    "."
                                } else {
                                    ", "
                                }
                            }
                            Text(
                                text = appendString,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                        }
                    }
                }
            }
        }
    }

//Customer DrinkItem
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DrinkItem(drink: Drink, onSelected: () -> Unit){
        Card(
            modifier = Modifier
                .clickable { onSelected() },
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape(20.dp),

        ) {
            Column {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Column( verticalArrangement = Arrangement.Center) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Text(
                                text = drink.name ?: "",
                                modifier = Modifier.padding(10.dp),
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                        Divider(modifier = Modifier.padding(4.dp))

                        GlideImage(
                            model = Uri.parse(drink.image ?: ""),
                            contentDescription = "Drink Image",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                        )

                        Divider(modifier = Modifier.padding(4.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(4.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            var appendString = ""
                            for (ingredient in drink.ingredients!!) {
                                appendString += ingredient.inventory?.name
                                appendString += if (drink.ingredients.lastIndex == drink.ingredients.indexOf(ingredient)) {
                                    "."
                                } else {
                                    ", "
                                }
                            }
                            Text(
                                text = appendString,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
            }
        }
    }

//Cart Drink Item
@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterialApi::class)
@Composable
fun CartDrinkItem(drink: Drink, onSelected: () -> Unit,
                  onDeletePressed: () -> Unit = {}) {
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
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
            }
        }
        Surface(
            modifier = Modifier
                .offset { IntOffset(swipeableState.offset.value.toInt(), 0) }
//                .border(
//                    width = 1.dp,
//                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
//                    shape = RoundedCornerShape(4.dp)
//                )
                .clickable { onSelected() },
            tonalElevation = 5.dp,
            shape = RoundedCornerShape(20.dp),
        ) {
            Column() {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(verticalArrangement = Arrangement.Center) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Text(
                                text = drink.name ?: "",
                                modifier = Modifier.padding(10.dp),
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                        Divider(modifier = Modifier.padding(4.dp))

                        GlideImage(
                            model = Uri.parse(drink.image ?: ""),
                            contentDescription = "Drink Image",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                        )

                        Divider(modifier = Modifier.padding(4.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(4.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            var appendString = ""
                            for (ingredient in drink.ingredients!!) {
                                appendString += ingredient.inventory?.name
                                appendString += if (drink.ingredients.lastIndex == drink.ingredients.indexOf(
                                        ingredient
                                    )
                                ) {
                                    "."
                                } else {
                                    ", "
                                }
                            }
                            Text(
                                text = appendString,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
