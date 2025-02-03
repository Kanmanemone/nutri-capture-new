package com.example.nutri_capture_new.nutrient

import com.example.nutri_capture_new.db.DayMeal
import com.example.nutri_capture_new.db.Meal

sealed class NutrientViewModelEvent {
    data class UpdateInputtedDayMeal(val dayMeal: DayMeal) : NutrientViewModelEvent()
    data object InsertInputtedDayMeal : NutrientViewModelEvent()
    data class DeleteDayMeal(val dayMeal: DayMeal) : NutrientViewModelEvent()
}