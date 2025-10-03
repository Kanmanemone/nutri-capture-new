package com.example.database

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
           meal_table.overeating_value AS overeating_value,
           meal_table.refined_sugar_value AS refined_sugar_value,
           meal_table.refined_grain_value AS refined_grain_value,
           meal_table.flour_value AS flour_value,
           meal_table.fiber_value AS fiber_value,
           meal_table.protein_value AS protein_value,
           meal_table.sodium_value AS sodium_value
           /* NutritionInfo의 Column 끝 */
    FROM day_table
    INNER JOIN meal_table ON meal_table.day_id = day_table.day_id
    ORDER BY day_table.day_date DESC,
             meal_table.meal_time DESC,
             meal_table.meal_id DESC
""")
data class DayMeal(
    @ColumnInfo(name = "day_id")
    val dayId: Long = 0,

    @ColumnInfo(name = "meal_id")
    val mealId: Long = 0,

    @ColumnInfo(name = "day_date")
    var date: LocalDate = LocalDate.now(),

    @ColumnInfo(name = "meal_time")
    var time: LocalTime = LocalTime.now(),

    @ColumnInfo(name = "meal_name")
    var name: String = "",

    @Embedded
    val nutritionInfo: NutritionInfo = NutritionInfo()
) {
    operator fun compareTo(otherDayMeal: DayMeal): Int {
        return compareValuesBy(this, otherDayMeal,
            { it.date }, // (1차) date에 대해 오름차순으로 비교 (date가 큰 쪽이 비교 우위)
            { it.time }, // (2차) time에 대해 오름차순으로 비교 (time이 큰 쪽이 비교 우위)
            { it.mealId } // (3차) mealId에 대해 오름차순으로 비교 (mealId가 큰 쪽이 비교 우위)
        ) * -1 // 부호를 반전시키면, 내림차순으로 비교한 것과 동일한 결과가 나옴
    }
}