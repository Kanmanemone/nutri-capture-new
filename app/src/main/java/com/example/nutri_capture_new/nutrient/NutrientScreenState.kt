package com.example.nutri_capture_new.nutrient

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.nutri_capture_new.db.NutritionInfo
import java.time.LocalDate
import java.time.LocalTime

data class NutrientScreenState(
    val dailyMeals: SnapshotStateList<DailyMeal>
)

data class DailyMeal(
    var date: LocalDate,
    val meals: SnapshotStateList<Meal>
)

data class Meal(
    var time: LocalTime,
    var name: String,
    val nutritionInfo: NutritionInfo,
)