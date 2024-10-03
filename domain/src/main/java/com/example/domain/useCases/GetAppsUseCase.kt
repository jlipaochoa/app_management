package com.example.domain.useCases

import com.example.domain.models.AppInfoAnalysisState
import com.example.domain.models.toAppInfoAnalysisState
import com.example.domain.repository.StatsInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAppsUseCase @Inject constructor(private val statsInterface: StatsInterface) {

    suspend operator fun invoke(typeAnalysis: TypeAnalysis): List<AppInfoAnalysisState> {
        return withContext(Dispatchers.IO) {
            val items = statsInterface.getLaunchAppsWithStats().map { it.toAppInfoAnalysisState() }

            when (typeAnalysis) {
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
}

enum class TypeAnalysis {
    Usage,
    Memory,
    Security
}