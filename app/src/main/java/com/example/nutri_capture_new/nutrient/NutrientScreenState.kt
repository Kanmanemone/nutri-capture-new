package com.example.nutri_capture_new.nutrient

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.nutri_capture_new.db.Meal
import java.time.LocalDate

data class NutrientScreenState(
    val listOfDateAndMeals: SnapshotStateList<DateAndMeals>
)

data class DateAndMeals(
    val date: LocalDate,
    val meals: SnapshotStateList<Meal>
)