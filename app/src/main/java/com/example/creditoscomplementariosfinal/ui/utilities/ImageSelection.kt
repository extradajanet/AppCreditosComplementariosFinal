package com.example.creditoscomplementariosfinal.ui.utilities

import androidx.annotation.DrawableRes
import com.example.creditoscomplementariosfinal.R

@DrawableRes
fun getIconForImageName(imageName: String?): Int {
    return when (imageName) {
        "img1E.jpg", "img2E.jpg", "img3E.jpg", "img4E.jpg", "img5E.jpg" -> R.drawable.actividad
        "imagen1.png", "imagen2.png", "imagen3.png", "imagen4.png", "imagen5.png" -> R.drawable.course__2_
        else -> R.drawable.logo // imagen por defecto
    }
}
