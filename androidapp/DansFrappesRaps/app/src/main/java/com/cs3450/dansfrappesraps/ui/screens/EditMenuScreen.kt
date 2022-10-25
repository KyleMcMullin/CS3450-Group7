package com.cs3450.dansfrappesraps.ui.screens

import android.media.Image
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cs3450.dansfrappesraps.R
import com.cs3450.dansfrappesraps.ui.components.IngredientItem
import com.cs3450.dansfrappesraps.ui.components.LabelledTextInput
import com.cs3450.dansfrappesraps.ui.components.Loader
import com.cs3450.dansfrappesraps.ui.viewmodels.EditMenuViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun EditMenuScreen(navController: NavController, id: String?) {
    var viewModel: EditMenuViewModel = viewModel()
    var scope = rememberCoroutineScope()
    var state = viewModel.uiState

    val selectImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        state.image = uri
        state.isImage = true
    }

    LaunchedEffect(true) {
        val loadingIngredients = async { viewModel.getIngredients() }
        delay(2000)
        loadingIngredients.await()
        viewModel.setUpInitialState(id)
        state.loading = false
    }
    if (state.loading) {
        Spacer(modifier = Modifier.height(16.dp))
        Loader()
    }
    else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LabelledTextInput(
                value = state.name,
                label = "Drink Name",
                onValueChange = { state.name = it },
                placeholder = { Text("Drink Name") },
                error = false
            )
            if (state.isImage) {
                Button(onClick = { state.showImage = !state.showImage }) {
                    Text("Show Image")
                }
                Box(Modifier.padding(8.dp).height(IntrinsicSize.Min)) {
                    androidx.compose.animation.AnimatedVisibility(
                        visible = state.showImage,
                        enter = expandVertically(),
                        exit = shrinkVertically()
                    ) {
                        GlideImage(
                            model = state.image,
                            contentDescription = "Drink Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
            else {
                Button(onClick = { selectImageLauncher.launch("image/*") }) {
                    Text("Select Image")
                }
            }
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(state.ingredients, key = { it.inventory?.id!! }) { ingredient ->
                    IngredientItem(
                        ingredient = ingredient,
                        onMinusPressed = {viewModel.decrementIngredient(ingredient)},
                        onPlusPressed = { viewModel.incrementIngredient(ingredient) },
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            Button(onClick = {
                scope.launch {
                    if (id == null || id == "new") {
                        viewModel.addDrink()
                    }
                    else {
                        viewModel.updateDrink(id)
                    }
                    navController.popBackStack()
                }
                             }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Save")
            }
        }
    }
}