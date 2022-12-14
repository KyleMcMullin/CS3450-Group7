package com.cs3450.dansfrappesraps.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.cs3450.dansfrappesraps.ui.models.User
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ManagePayrollItem(
    user: User,
    onSaveHours: () -> Unit = {},
    onApprove: () -> Unit = {},
    onEditRate: () -> Unit = {}
) {
    val swipeableState = rememberSwipeableState(initialValue = SwipeState.CLOSED)
    var showDetail by remember {mutableStateOf(false)}
    var scope = rememberCoroutineScope()
    val anchors = mapOf(
        0f to SwipeState.CLOSED,
        -200f to SwipeState.OPEN
    )
    showDetail = false
    var textHours by remember { mutableStateOf(user.hours.toString()) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                orientation = Orientation.Horizontal
            )
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            horizontalArrangement = Arrangement.End) {
            Button(
                onClick = {
                    scope.launch {swipeableState.snapTo(SwipeState.CLOSED)}
                    onApprove()
                          },
                modifier = Modifier
//                    .fillMaxHeight()
                    .fillMaxWidth(.5f),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.primary)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Outlined.Done, contentDescription = "Approve")
                }
            }
        }
        Surface(
            modifier = Modifier
                .offset { IntOffset(swipeableState.offset.value.toInt(), 0) }
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(4.dp)
                )
                .clickable { showDetail = !showDetail },
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
                            Text(text = user.name ?: "", modifier = Modifier.padding(10.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Pay Rate: ${user.payRate}",
                                modifier = Modifier.padding(10.dp),
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Text(text = "Hours: ${user.hours}", modifier = Modifier.padding(10.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Balance: ${user.balance}",
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                        AnimatedVisibility(
                            visible = showDetail,
                            enter = expandVertically(),
                            exit = shrinkVertically()
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Divider(modifier = Modifier.padding(4.dp))
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    LabelledTextInput(
                                        placeholder = { Text(text="Hours") },
                                        value = textHours,
                                        onValueChange = {
                                            if (it != "") {
                                                user.hours = it.toInt()
                                            }
                                            textHours = it
                                        },
                                        label = "Edit Hours",
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                    )
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Row(horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                                    Button(onClick = onSaveHours) {
                                        Text(text = "Save Hours")
                                    }
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Button(onClick = onEditRate) {
                                        Text(text = "Edit Pay Rate")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}