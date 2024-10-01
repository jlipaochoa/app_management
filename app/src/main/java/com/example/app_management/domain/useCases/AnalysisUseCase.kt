package com.example.app_management.domain.useCases

import com.example.app_management.domain.models.AppInfoAnalysis
import com.example.app_management.presentation.apps.components.TypeAnalysis
import javax.inject.Inject

class AnalysisUseCase @Inject constructor() {
    operator fun invoke(apps: List<AppInfoAnalysis>): String {
        var description = "Te muestro al menos 10 apps para cada tipo de análisis:\n\n"
        description += "Estas apps son las más pesadas, revisa si es necesario tenerlas:\n"
        apps.sortedByDescending { it.size }.take(10).forEachIndexed { index, app ->
            description += "${index + 1}. ${app.name} - Tamaño: ${app.size} MB\n"
        }

        description += "\nEstas apps tienen el mayor riesgo de seguridad, revisa los permisos:\n"
        apps.filter { it.percentageRisk > 50 }.sortedByDescending { it.percentageRisk }
            .forEachIndexed { index, app ->
                description += "${index + 1}. ${app.name} - Riesgo de seguridad: ${app.percentageRisk}%\n"
            }

        description += "\nEstas apps son las que menos usas, considera eliminarlas:\n"
        apps.filter { it.percentageUsage <= 10}.sortedByDescending { it.percentageUsage }
            .take(10)
            .forEachIndexed { index, app ->
                description += "${index + 1}. ${app.name} - Uso: ${app.percentageUsage}%\n"
            }
        return description
    }
}