package com.example.app_management.data

import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color

class AppInfoModel(
    val name: String,
    val image: Drawable,
    val size: Double = 0.0,
    val percentageRisk: Double = 0.0,
    var percentageUsage: Double = 0.0,
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
        percentageUsage > 50 -> "Lo usas Constantemente"
        percentageUsage > 25 -> "Lo usas a veces"
        percentageUsage == 0.0 -> "No lo usas"
        else -> "Lo usas poco"
    }
}
