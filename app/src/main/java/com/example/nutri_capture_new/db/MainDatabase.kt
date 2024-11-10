package com.example.nutri_capture_new.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        Day::class,
        Meal::class
    ],
    views = [DayMealView::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class MainDatabase : RoomDatabase() {

    abstract val mainDAO: MainDAO

    companion object {
        @Volatile
        private var INSTANCE: MainDatabase? = null
        fun getInstance(context: Context): MainDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MainDatabase::class.java,
                        "main_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}