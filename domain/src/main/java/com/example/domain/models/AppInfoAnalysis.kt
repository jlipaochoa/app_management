package com.example.domain.models

import android.graphics.drawable.Drawable

class AppInfoAnalysis(
    val name: String,
    val image: Drawable,
    val size: Double = 0.0,
    val percentageRisk: Double,
    val percentageUsage: Double,
    val packageName: String = "",
    val systemApp: Boolean = false,
    val version: String? = null,
    val versionCode: Int? = null,
    val permissions: List<Pair<String, Boolean>>,
    val firstInstallTime: String? = null,
    val lastUpdateTime: String? = null,
)


fun AppInfoAnalysis.toAppInfoAnalysisState(): AppInfoAnalysisState {
    return AppInfoAnalysisState(
        name,
        image,
        size,
        percentageRisk,
        percentageUsage,
        packageName,
        systemApp,
        this.getColorRisk(),
        this.getColorUsage(),
        this.labelUsage()
    )
}

fun AppInfoAnalysis.toAppInfoDetailState(): AppInfoDetailState {
    return AppInfoDetailState(
        name = this.name,
        packageName = this.packageName,
        image = this.image,
        version = this.version,
        versionCode = this.versionCode,
        permissions = this.permissions,
        firstInstallTime = this.firstInstallTime,
        lastUpdateTime = this.lastUpdateTime,
        isSystemApp = this.systemApp,
        sizeApp = this.size,
        securityPercentages = this.percentageRisk,
        percentageUsage = this.percentageUsage,
    )
}

fun AppInfoAnalysis.getColorRisk(): Long {
    return when {
        percentageRisk > 50 -> 0xFFEF6969
        percentageRisk > 25 -> 0xFFFFEB3B
        else -> 0xFF7CFFA3
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

fun AppInfoAnalysis.getColorUsage(): Long {
    return when {
        percentageUsage > 50 -> 0xFF7CFFA3
        percentageUsage > 11 -> 0xFFFFA500
        percentageUsage > 0 -> 0xFFFFEB3B
        else -> 0xFFEF6969
    }
}
