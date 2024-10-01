package com.example.app_management.domain.models

import android.graphics.drawable.Drawable
import java.util.Date

class AppInfoDetail(
    val name: String,
    val packageName: String,
    var description: String = "",
    val image: Drawable,
    val version: String? = null,
    val versionCode: Int? = null,
    val permissions: List<Pair<String, Boolean>>,
    var category: String = "Ninguno",
    val firstInstallTime: String? = null,
    val lastUpdateTime: String? = null,
    val isSystemApp: Boolean,
    val sizeApp: Double,
    var securityPercentages: Double = 0.0,
    var percentageUsage: Double = 0.0,
    var analysisPermission: String = "",
    var analysisUsage: String = ""
)

fun AppInfoDetail.toAppInfo(): AppInfo {
    return AppInfo(
        packageName = packageName,
        description = description,
        category = category
    )
}