package com.cs3450.dansfrappesraps.ui.components


import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.ui.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cs3450.dansfrappesraps.R
import com.cs3450.dansfrappesraps.ui.theme.Shapes

/*
Author: Hamilton Hardy
TODO: On click behaviour
 change default image to drink item
 Change content description
 */



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuItem(name:String = "Menu Item", imageId: Int = R.drawable.emptyimage,imageDescription: String = "DEFAULT IMAGE") {
            Card(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .size(150.dp, 150.dp)
                    .padding(10.dp)
                ,
                enabled = true,
                colors = CardDefaults.elevatedCardColors(),
                elevation = CardDefaults.cardElevation(5.dp),
                shape = Shapes.medium
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Text(name)

                }
                Divider(thickness = 2.dp, color = DividerDefaults.color)
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                ){
                    Image(
                        modifier = Modifier
                            .fillMaxWidth(),
                        painter = painterResource(id = imageId),
                        contentDescription = imageDescription,
                        contentScale = ContentScale.Crop)

                }
            }
        }