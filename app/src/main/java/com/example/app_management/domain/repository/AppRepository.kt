package com.example.app_management.domain.repository

import com.example.app_management.domain.models.AppInfo

interface AppRepository {
    suspend fun getAppInfoByPackageName(packageName: String): AppInfo?
    suspend fun insertAppInfo(appInfo: AppInfo)
}