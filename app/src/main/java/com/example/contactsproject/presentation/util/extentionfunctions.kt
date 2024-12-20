package com.example.contactsproject.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import kotlin.math.abs

fun Long.formatDate(): String {
    val date = java.util.Date(this) // Convert the Long timestamp to Date
    val format = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault())
    return format.format(date) // Format the date to the desired format
}
@Composable
fun String.getAvatarColor(): Color {
    val colors = listOf(
        Color(0xFF7C4DFF),
        Color(0xFF64B5F6),
        Color(0xFF81C784),
        Color(0xFFFFB74D),
        Color(0xFFE57373)
    )
    val index = this.hashCode() % colors.size
    return colors[abs(index)]
}