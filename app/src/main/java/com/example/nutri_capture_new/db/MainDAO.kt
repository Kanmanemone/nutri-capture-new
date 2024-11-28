package com.example.nutri_capture_new.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.time.LocalDate
import java.time.LocalTime

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

    @Query("""
    SELECT meal_table.* 
    FROM meal_table 
    INNER JOIN day_table ON meal_table.day_id = day_table.day_id 
    WHERE day_table.day_date = :targetDate 
    ORDER BY meal_table.meal_time ASC
    """)
    suspend fun getMealsOrderedByTime(targetDate: LocalDate): List<Meal>

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
    suspend fun getAllDayMeals(): List<DayMealView>

    @Query("""
    SELECT * FROM DayMealView
    ORDER BY day_date DESC,
             meal_time DESC,
             meal_id DESC
    LIMIT :limit
    """)
    suspend fun getAllDayMeals(limit: Int): List<DayMealView>

    @Query("""
    SELECT * FROM DayMealView 
    WHERE day_date < :lastDate
       OR (day_date = :lastDate AND meal_time < :lastTime)
       OR (day_date = :lastDate AND meal_time = :lastTime AND meal_id < :lastId)
    ORDER BY day_date DESC,
             meal_time DESC,
             meal_id DESC
    LIMIT :limit
    """)
    suspend fun getNextDayMealsAfter(
        lastDate: LocalDate,
        lastTime: LocalTime,
        lastId: Long,
        limit: Int
    ): List<DayMealView>

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