package com.example.nutri_capture_new.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.time.LocalDate

class MainRepository(private val dao: MainDAO) {
    suspend fun insertMeal(meal: Meal): Long {
        return dao.insertMeal(meal)
    }

    suspend fun deleteMeal(meal: Meal) {
        return dao.deleteMeal(meal)
    }

    suspend fun getMealsByDateOrderedByTime(targetDate: LocalDate): List<Meal> {
        return dao.getMealsByDateOrderedByTime(targetDate)
    }
}