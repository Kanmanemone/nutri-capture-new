package com.example.nutri_capture_new.db

import androidx.room.Database
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
}