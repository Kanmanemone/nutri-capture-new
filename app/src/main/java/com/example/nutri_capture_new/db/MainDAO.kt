package com.example.nutri_capture_new.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.time.LocalDate

@Dao
interface MainDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: Meal): Long

    @Delete
    suspend fun deleteMeal(meal: Meal)

    @Query("""
    SELECT meal_table.* 
    FROM meal_table 
    INNER JOIN day_table ON meal_table.day_id = day_table.day_id 
    WHERE day_table.day_date = :targetDate 
    ORDER BY meal_table.meal_time ASC
    """)
    suspend fun getMealsByDateOrderedByTime(targetDate: LocalDate): List<Meal>
}