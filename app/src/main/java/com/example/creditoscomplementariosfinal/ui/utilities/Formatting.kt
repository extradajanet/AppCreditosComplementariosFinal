package com.example.creditoscomplementariosfinal.ui.utilities

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(dateStr: String): String {
    val input = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val output = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return try {
        output.format(input.parse(dateStr)!!)
    } catch (e: Exception) {
        dateStr
    }
}

fun formatTime(timeStr: String): String {
    val input = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val output = SimpleDateFormat("HH:mm", Locale.getDefault())
    return try {
        output.format(input.parse(timeStr)!!)
    } catch (e: Exception) {
        timeStr
    }
}
