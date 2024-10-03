package com.example.domain.useCases

import com.example.domain.models.AppInfoDetailState
import com.example.domain.repository.AppRepository
import com.example.domain.models.toAppInfoDetailState
import com.example.domain.repository.StatsInterface
import javax.inject.Inject

class GetAppByPackageNameUseCase @Inject constructor(
    private val appRepository: AppRepository,
    private val statsInterface: StatsInterface
) {
    suspend operator fun invoke(packageName: String): AppInfoDetailState? {
        val appFromStats = statsInterface.getAppWithStats(packageName)
        val appInfoFromDb = appRepository.getAppInfoByPackageName(packageName)
        val appInfoAnalysis = appFromStats?.toAppInfoDetailState()
        appInfoAnalysis?.description = appInfoFromDb?.description ?: ""
        appInfoAnalysis?.category = appInfoFromDb?.category ?: "Ninguno"
        return appInfoAnalysis
    }
}