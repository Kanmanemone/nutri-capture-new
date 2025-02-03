package com.example.nutri_capture_new.nutrient

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.nutri_capture_new.db.DayMeal
import com.example.nutri_capture_new.db.Meal

data class NutrientScreenState(
    val dayMeals: SnapshotStateList<DayMeal>,
    var inputtedDayMeal: DayMeal
)