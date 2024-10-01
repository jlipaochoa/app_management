package com.example.app_management.data

import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color
import com.example.app_management.ui.theme.Orange

class AppInfoModel(
    val name: String,
    val image: Drawable,
    val size: Double = 0.0,
    val percentageRisk: Double = 0.0,
    var percentageUsage: Double = 0.0,
    val isSystemApp: Boolean = false
)

fun AppInfoModel.getColorRisk(): Color {
    return when {
        percentageRisk > 50 -> Color.Red
        percentageRisk > 25 -> Color.Yellow
        else -> Color.Green
    }
}

fun AppInfoModel.labelUsage(): String {
    return when {
        percentageUsage > 50 -> "Frecuente"
        percentageUsage > 11 -> "Regular"
        percentageUsage > 0 -> "De vez en cuando"
        else -> "No lo usas"
    }
}

fun AppInfoModel.colorUsage(): Color {
    return when {
        percentageUsage > 50 -> Color.Green
        percentageUsage > 11 -> Orange
        percentageUsage > 0 -> Color.Yellow
        else -> Color.Red
    }
}


