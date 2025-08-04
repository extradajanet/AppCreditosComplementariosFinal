package com.example.creditoscomplementariosfinal.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign


@Composable
fun ErrorModal(
    showDialog: Boolean,
    messageTitle: String,
    errorMessage: String,
    onDismiss: () -> Unit = {},
){
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(messageTitle, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold) },
            text = { Text(errorMessage,textAlign = TextAlign.Justify) },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("OK")
                }
            }
        )
    }
}
