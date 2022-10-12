package com.cs3450.dansfrappesraps.ui.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LabelledTextInput(
    value: String,
    label: String,
    onValueChange: (value: String) -> Unit,
    placeholder: @Composable () -> Unit,
    password: Boolean = false,
    error: Boolean = false,
    leadingIcon: @Composable () -> Unit = {},
    keyboardOptions: KeyboardOptions? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold),
        visualTransformation = if (password) PasswordVisualTransformation() else VisualTransformation.None,
        isError = error,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary
        ),
        label = { Text(text = label, color = MaterialTheme.colorScheme.primary )},
        leadingIcon = leadingIcon,
        keyboardOptions = keyboardOptions ?: KeyboardOptions.Default,
    )
    Spacer(modifier = Modifier.padding(5.dp))
}