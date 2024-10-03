package com.example.data.repository

import com.example.domain.models.AppInfo
import com.example.domain.repository.AppRepository
import com.example.data.AppDao

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