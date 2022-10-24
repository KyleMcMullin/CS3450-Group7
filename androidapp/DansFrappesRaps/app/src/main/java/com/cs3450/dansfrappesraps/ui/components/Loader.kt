package com.cs3450.dansfrappesraps.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cs3450.dansfrappesraps.R

@Composable
@Preview
fun Loader() {
    val transition = rememberInfiniteTransition()
    val sizePercentage by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            repeatMode = RepeatMode.Reverse,
            animation = tween(
                1000,
            )
        )
    )

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            modifier = Modifier
                .size(
                    ((50 * sizePercentage) + 50).dp
                ),
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 2.dp,
            color = Color.Transparent
        ) {
            Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",


        )}

        Text(text = "Loading...")
    }
}