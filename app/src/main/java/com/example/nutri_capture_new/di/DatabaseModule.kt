package com.example.nutri_capture_new.di

import android.content.Context
import androidx.room.Room
import com.example.nutri_capture_new.db.MainDAO
import com.example.nutri_capture_new.db.MainDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MainDatabase {
        return Room.databaseBuilder(
            context,
            MainDatabase::class.java,
            "main_database"
        ).build()
    }

    @Provides
    fun provideMainDAO(database: MainDatabase): MainDAO {
        return database.mainDAO
    }
}