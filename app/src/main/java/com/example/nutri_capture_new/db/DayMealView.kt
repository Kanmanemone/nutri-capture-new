package com.example.nutri_capture_new.db

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Embedded
import java.time.LocalDate
import java.time.LocalTime

/*
Day 테이블의 dayId와 Day의 자식인 Meal 테이블의 dayId가 같은(==) 조건으로 INNER JOIN하되,
첫째로는 Day.date에 대해 내림차순,
둘째로는 Meal.time에 대해 내림차순,
셋째로는 Meal.mealId에 대해 내림차순으로 정렬
*/
@DatabaseView("""
    SELECT day_table.day_id AS day_id,
           meal_table.meal_id AS meal_id,
           day_table.day_date AS day_date, 
           meal_table.meal_time AS meal_time,
           meal_table.meal_name AS meal_name,
           /* NutritionInfo의 Column 시작 */
           meal_table.overeating_excess AS overeating_excess,
           meal_table.refined_sugar_excess AS refined_sugar_excess,
           meal_table.refined_grain_excess AS refined_grain_excess,
           meal_table.flour_excess AS flour_excess,
           meal_table.fiber_quality AS fiber_quality,
           meal_table.protein_quality AS protein_quality,
           meal_table.sodium_excess AS sodium_excess
           /* NutritionInfo의 Column 끝 */
    FROM day_table
    INNER JOIN meal_table ON meal_table.day_id = day_table.day_id
    ORDER BY day_table.day_date DESC,
             meal_table.meal_time DESC,
             meal_table.meal_id DESC
""")
data class DayMealView(
    @ColumnInfo(name = "day_id")
    val dayId: Long,

    @ColumnInfo(name = "meal_id")
    val mealId: Long,

    @ColumnInfo(name = "day_date")
    var date: LocalDate,

    @ColumnInfo(name = "meal_time")
    var time: LocalTime,

    @ColumnInfo(name = "meal_name")
    var name: String,

    @Embedded
    val nutritionInfo: NutritionInfo
)