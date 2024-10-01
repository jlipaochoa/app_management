package com.example.app_management.domain.useCases

import android.app.Application
import com.example.app_management.domain.models.AppInfoAnalysis
import com.example.app_management.extentions.getLaunchApps
import com.example.app_management.extentions.timesUsageStats
import com.example.app_management.domain.models.toAppInfoModel
import com.example.app_management.presentation.apps.components.TypeAnalysis
import javax.inject.Inject

class GetAppsUseCase @Inject constructor(private val application: Application) {

    operator fun invoke(typeAnalysis: TypeAnalysis): List<AppInfoAnalysis> {
        val times = application.timesUsageStats()
        val items = application.packageManager.getLaunchApps()
            .map {
                it.toAppInfoModel(
                    application.packageManager,
                    application.applicationContext,
                    times
                )
            }
        return when(typeAnalysis){
            TypeAnalysis.Memory -> {
                items.sortedByDescending { it.size }
            }
            TypeAnalysis.Security -> {
                items.sortedByDescending { it.percentageRisk }
                }
            TypeAnalysis.Usage -> {
                items.sortedByDescending { it.percentageUsage }
            }
        }
    }
}

