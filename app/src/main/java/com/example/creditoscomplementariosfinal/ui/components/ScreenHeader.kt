package com.example.creditoscomplementariosfinal.ui.components


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.creditoscomplementariosfinal.R

@Composable
fun ScreenHeader(
    title: String,
    showBackButton: Boolean = true,
    onBackClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showBackButton) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White
                )
            }
        }

        Text(
            text = title,
            lineHeight = 35.sp,
            color = colorResource(id = R.color.white),
            fontFamily = FontFamily(Font(R.font.murecho_bold)),
            fontSize = 30.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp, end = 8.dp)
        )
    }
}
