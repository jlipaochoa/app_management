package com.example.app_management.data

import android.graphics.drawable.Drawable
import java.util.Date

class AnalysisAppModel(
    val name: String,
    val image: Drawable,
    val version: String? = null,
    val versionCode: String? = null,
    val permissions: Pair<String, Boolean>,
    val category: Int,
    val firstInstallTime: Date? = null,
    val lastUpdateTime: Date? = null,
    val isSystemApp: Boolean,
    val sizeApp: Long
)