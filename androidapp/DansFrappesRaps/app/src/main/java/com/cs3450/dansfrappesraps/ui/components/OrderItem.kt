package com.cs3450.dansfrappesraps.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.magnifier
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.cs3450.dansfrappesraps.ui.theme.Shapes
import java.nio.ByteOrder

/*
Author: Hamilton Hardy
TODO: On click behaviour, Modify Default size, Hold to complete text style
Parameters to Specify: Customer Name, Customer Order
 */

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun OrderItem(customerName:String = "Customer Name",customerOrder: Array<String> = arrayOf("Drink1","Drink2")){
    Card(modifier = Modifier
        .size(296.dp, 150.dp)
        .padding(5.dp)
        .combinedClickable(onClick = {}, onLongClick = { /*TODO*/ }),

        colors = CardDefaults.elevatedCardColors(),
        elevation = CardDefaults.cardElevation(5.dp),
        shape = Shapes.medium) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Text(customerName)

        }
        Divider(thickness = 2.dp, color = DividerDefaults.color)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) { Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            for (drink in customerOrder){
                Text(drink, modifier = Modifier.padding(5.dp))
            }
            Spacer(modifier = Modifier.weight(1f))
            Text("Hold to Complete", modifier = Modifier.padding(5.dp))
            }
        }
    }
}