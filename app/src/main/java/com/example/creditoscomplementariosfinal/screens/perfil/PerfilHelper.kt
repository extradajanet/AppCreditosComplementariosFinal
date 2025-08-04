package com.example.creditoscomplementariosfinal.screens.perfil

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun PerfilField(
    label: String,
    value: String,
    isEditing: Boolean,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false
) {
    Row (modifier = Modifier.padding(8.dp)) {
        Text(text = "$label: " ,color = Color.White, modifier = Modifier.padding(end = 8.dp))
        if (isEditing) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None

            )
        } else {
            Text(text = value, color = Color.White)
        }
    }
}
