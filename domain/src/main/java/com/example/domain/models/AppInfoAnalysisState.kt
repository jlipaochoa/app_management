package com.example.domain.models

import android.graphics.drawable.Drawable

class AppInfoAnalysisState(
    val name: String,
    val image: Drawable,
    val size: Double = 0.0,
    val percentageRisk: Double = 0.0,
    var percentageUsage: Double = 0.0,
    val packageName: String = "",
    val systemApp: Boolean = false,
    val colorRisk: Long = 0L,
    val colorUsage: Long = 0L,
    val descriptionUsage: String = ""
)