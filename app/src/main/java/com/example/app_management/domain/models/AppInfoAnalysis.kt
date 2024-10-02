package com.example.app_management.domain.models

import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color
import com.example.app_management.presentation.ui.theme.Green40
import com.example.app_management.presentation.ui.theme.Orange
import com.example.app_management.presentation.ui.theme.Red80
import com.example.app_management.presentation.ui.theme.Yellow80

class AppInfoAnalysis(
    val name: String,
    val image: Drawable,
    val size: Double = 0.0,
    val percentageRisk: Double = 0.0,
    var percentageUsage: Double = 0.0,
    val packageName: String = "",
    val systemApp: Boolean = false
)

fun AppInfoAnalysis.getColorRisk(): Color {
    return when {
        percentageRisk > 50 -> Red80
        percentageRisk > 25 -> Yellow80
        else -> Green40
    }
}

fun AppInfoAnalysis.labelUsage(): String {
    return when {
        percentageUsage > 50 -> "Frecuente"
        percentageUsage > 11 -> "Regular"
        percentageUsage > 0 -> "De vez en cuando"
        else -> "No lo usas"
    }
}

fun AppInfoAnalysis.colorUsage(): Color {
    return when {
        percentageUsage > 50 -> Green40
        percentageUsage > 11 -> Orange
        percentageUsage > 0 -> Yellow80
        else -> Red80
    }
}


