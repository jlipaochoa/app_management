package com.example.app_management.domain.useCases

import com.example.domain.models.AppInfo
import com.example.domain.repository.AppRepository
import javax.inject.Inject

class InsertAppInfoUseCase @Inject constructor(
    private val appRepository: AppRepository
) {

    suspend operator fun invoke(appInfo: AppInfo) {
        appRepository.insertAppInfo(appInfo)
    }

}