package com.example.app_management.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.app_management.domain.models.AppInfo

@Database(
    entities = [AppInfo::class],
    version = 3
)
abstract class AppDataBase: RoomDatabase() {

    abstract val appDao: AppDao

    companion object {
        const val DATABASE_NAME = "app_info_db"
    }
}