package com.example.domain.repository

import com.example.domain.models.AppInfoAnalysis

interface StatsInterface {
    fun getLaunchAppsWithStats(): List<AppInfoAnalysis>
    fun getAppSize(packageName: String): Double
    fun getPermissionApps(packageName: String):List<String>
    fun getAppWithStats(packageName: String):AppInfoAnalysis?
}

