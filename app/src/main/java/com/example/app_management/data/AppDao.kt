package com.example.app_management.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.app_management.domain.models.AppInfo

@Dao
interface AppDao {
    @Query("SELECT * FROM AppInfo WHERE packageName = :packageName")
    fun getAppInfo(packageName: String): AppInfo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppInfo(appInfo: AppInfo)
}