package com.example.app_management.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.domain.repository.AppRepository
import com.example.app_management.presentation.apps.CoroutineContextProvider
import com.example.app_management.presentation.ui.PermissionChecker
import com.example.app_management.presentation.ui.UsageStatsPermissionChecker
import com.example.data.AppDataBase
import com.example.data.repository.AppRepositoryImpl
import com.example.data.repository.StatsInterfaceImpl
import com.example.domain.repository.StatsInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): AppDataBase {
        return Room.databaseBuilder(
            app,
            AppDataBase::class.java,
            AppDataBase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: AppDataBase): AppRepository {
        return AppRepositoryImpl(db.appDao)
    }

    @Provides
    @Singleton
    fun providePermissionChecker(): PermissionChecker {
        return UsageStatsPermissionChecker()
    }

    @Provides
    @Singleton
    fun provideCoroutineContext(): CoroutineContextProvider {
        return CoroutineContextProvider(Dispatchers.Main, Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideStatsInterface(application: Application): StatsInterface {
        return StatsInterfaceImpl(application)
    }
}