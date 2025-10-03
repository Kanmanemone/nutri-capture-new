package com.example.nutri_capture_new.nutrient

import com.example.database.DayMeal

sealed class NutrientViewModelEvent {
    data class UpdateInputtedDayMeal(val dayMeal: DayMeal) : NutrientViewModelEvent()
    data object InsertInputtedDayMeal : NutrientViewModelEvent()
    data class DeleteDayMeal(val dayMeal: DayMeal) : NutrientViewModelEvent()
}