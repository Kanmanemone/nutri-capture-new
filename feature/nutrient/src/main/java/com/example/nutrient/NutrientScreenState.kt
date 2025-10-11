package com.example.nutrient

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.database.DayMeal

data class NutrientScreenState(
    val dayMeals: SnapshotStateList<DayMeal>,
    var inputtedDayMeal: DayMeal
)