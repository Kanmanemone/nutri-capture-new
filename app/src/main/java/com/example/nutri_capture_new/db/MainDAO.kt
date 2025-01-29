package com.example.nutri_capture_new.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface MainDAO {
    @Query("SELECT day_id FROM day_table WHERE day_date = :date LIMIT 1")
    suspend fun getDayId(date: LocalDate): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDay(day: Day): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: Meal): Long

    @Delete
    suspend fun deleteMeal(meal: Meal): Int

    @Query("DELETE FROM meal_table WHERE meal_id = :mealId")
    suspend fun deleteMeal(mealId: Long): Int

    @Query("SELECT COUNT(*) FROM meal_table WHERE day_id = :dayId")
    suspend fun getMealCountForDay(dayId: Long): Int

    @Query("DELETE FROM day_table WHERE day_id = :dayId")
    suspend fun deleteDay(dayId: Long)

    @Query("""
    SELECT * FROM DayMealView
    ORDER BY day_date DESC,
             meal_time DESC,
             meal_id DESC
    """)
    fun getAllDayMeals(): Flow<List<DayMealView>>

    @Query("""
    SELECT * FROM DayMealView
    WHERE meal_id = :mealId
    ORDER BY day_date DESC,
             meal_time DESC,
             meal_id DESC
    LIMIT 1
    """)
    suspend fun getDayMeal(mealId: Long): DayMealView
}