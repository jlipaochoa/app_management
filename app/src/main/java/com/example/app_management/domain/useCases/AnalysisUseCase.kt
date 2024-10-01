package com.example.app_management.domain.useCases

import com.example.app_management.domain.models.AppInfoModel
import com.example.app_management.presentation.apps.components.TypeAnalysis
import javax.inject.Inject

class AnalysisUseCase @Inject constructor() {
    operator fun invoke(apps: List<AppInfoModel>, typeAnalysis: TypeAnalysis): String {
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