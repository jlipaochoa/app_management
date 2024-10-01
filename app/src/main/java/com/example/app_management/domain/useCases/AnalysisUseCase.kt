package com.example.app_management.domain.useCases

import com.example.app_management.domain.models.AppInfoAnalysis
import com.example.app_management.presentation.apps.components.TypeAnalysis
import javax.inject.Inject

class AnalysisUseCase @Inject constructor() {
    operator fun invoke(apps: List<AppInfoAnalysis>, typeAnalysis: TypeAnalysis): String {
        val description = "Te sugiero lo siguiente:"
        when(typeAnalysis){
            TypeAnalysis.Memory -> {

            }
            TypeAnalysis.Security -> {
                apps.sortedByDescending { it.percentageRisk }
            }

            TypeAnalysis.Usage -> {

            }
        }
        return " "
    }
}