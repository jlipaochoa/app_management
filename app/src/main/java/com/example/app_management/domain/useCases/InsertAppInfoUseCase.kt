package com.example.app_management.domain.useCases

import com.example.app_management.domain.models.AppInfo
import com.example.app_management.domain.repository.AppRepository
import javax.inject.Inject

class InsertAppInfoUseCase @Inject constructor(
    private val appRepository: AppRepository
) {

    suspend operator fun invoke(appInfo: AppInfo) {
        appRepository.insertAppInfo(appInfo)
    }

}