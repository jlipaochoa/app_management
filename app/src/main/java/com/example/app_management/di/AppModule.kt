package com.example.app_management.di

import android.app.Application
import androidx.room.Room
import com.example.app_management.data.AppDataBase
import com.example.app_management.data.repository.AppRepositoryImpl
import com.example.app_management.domain.repository.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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

}