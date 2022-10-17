package com.cs3450.dansfrappesraps.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.cs3450.dansfrappesraps.R
import com.cs3450.dansfrappesraps.ui.navigation.Routes
import com.cs3450.dansfrappesraps.ui.viewmodels.SplashScreenViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navHostController: NavHostController) {
    val viewModel: SplashScreenViewModel = viewModel()
    LaunchedEffect(true) {
        delay(1000)

        navHostController.navigate(
            if(viewModel.isUserLoggedIn()) {
                Routes.app.route
            } else {
                Routes.signIn.route
            }
        ) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                inclusive = true
            }
        }
    }
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxHeight(.33F).fillMaxWidth()){}
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Fit,
                    contentDescription = "Logo" ,
                    modifier = Modifier
                        .size(250.dp)
                        .clip(CircleShape)
                        .padding(10.dp)
                )

            }
            Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "Dan's Frappes & Raps",
                    modifier = Modifier.fillMaxWidth().padding(15.dp),
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}