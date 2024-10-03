package com.example.app_management.domain.useCases

import com.example.domain.models.AnalysisTotalInfo
import com.example.domain.models.AppInfoAnalysisState
import javax.inject.Inject

class AnalysisUseCase @Inject constructor() {
    operator fun invoke(apps: List<AppInfoAnalysisState>): AnalysisTotalInfo {
        val systemFromPlayStore = apps.filter { !it.systemApp }
        var descriptionSize = ""
        val listAppsSize = systemFromPlayStore.sortedByDescending { it.size }.take(10)
        listAppsSize.forEachIndexed { index, app ->
            descriptionSize += "${index + 1}. ${app.name} - Tamaño: ${app.size} MB\n"
        }

        var descriptionRisk = ""
        val listAppsRisk = apps
            .filter { it.percentageRisk > 50 }
            .sortedByDescending { it.percentageRisk }
        listAppsRisk.forEachIndexed { index, app ->
            descriptionRisk +=
                "${index + 1}. ${app.name} - Riesgo de seguridad: ${app.percentageRisk}%\n"
        }

        var descriptionUsage = ""
        val listAppsUsage = systemFromPlayStore
            .filter { it.percentageUsage <= 10 }
            .sortedByDescending { it.percentageUsage }
            .take(10)
        listAppsUsage.forEachIndexed { index, app ->
            descriptionUsage += "${index + 1}. ${app.name} - Uso: ${app.percentageUsage}%\n"
        }
        return AnalysisTotalInfo(
            descriptionUsage,
            descriptionRisk,
            descriptionSize
        )
    }
}

