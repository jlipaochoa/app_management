package com.example.app_management.domain.useCases

import android.app.Application
import com.example.app_management.domain.models.AppInfoDetail
import com.example.app_management.domain.models.toAppInfoDetail
import com.example.app_management.domain.repository.AppRepository
import com.example.app_management.extentions.getLaunchApps
import com.example.app_management.extentions.timesUsageStats
import javax.inject.Inject

class GetAppByPackageNameUseCase @Inject constructor(
    private val appRepository: AppRepository,
    private val application: Application
) {
    suspend operator fun invoke(packageName: String): AppInfoDetail? {
        val times = application.timesUsageStats()
        val appInfoFromDb = appRepository.getAppInfoByPackageName(packageName)
        val appFromPackageManager = application
            .packageManager
            .getLaunchApps()
            .firstOrNull { packageName == it.packageName } ?: return null
        val appInfoAnalysis =
            appFromPackageManager.toAppInfoDetail(application.packageManager, application, times)
        appInfoAnalysis.description = appInfoFromDb?.description ?: ""
        appInfoAnalysis.category = appInfoFromDb?.category ?: "Ninguno"
        return appInfoAnalysis
    }
}