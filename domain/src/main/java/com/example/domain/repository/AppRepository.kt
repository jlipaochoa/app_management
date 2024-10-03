package com.example.domain.repository

import com.example.domain.models.AppInfo

interface AppRepository {
    suspend fun getAppInfoByPackageName(packageName: String): AppInfo?
    suspend fun insertAppInfo(appInfo: AppInfo)
}