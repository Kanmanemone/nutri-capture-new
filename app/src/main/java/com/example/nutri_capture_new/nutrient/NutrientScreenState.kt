package com.example.nutri_capture_new.nutrient

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.nutri_capture_new.db.DayMealView

data class NutrientScreenState(
    val dayMeals: SnapshotStateList<DayMealView>,
    var inputtedMealName: String
)