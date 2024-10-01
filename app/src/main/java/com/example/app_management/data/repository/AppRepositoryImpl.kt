package com.example.app_management.data.repository

import com.example.app_management.data.AppDao
import com.example.app_management.domain.models.AppInfo
import com.example.app_management.domain.repository.AppRepository

class AppRepositoryImpl(
    private val appDao: AppDao
) : AppRepository {
    override suspend fun getAppInfoByPackageName(packageName: String): AppInfo? {
        return appDao.getAppInfo(packageName)
    }

    override suspend fun insertAppInfo(appInfo: AppInfo) {
        appDao.insertAppInfo(appInfo)
    }

}