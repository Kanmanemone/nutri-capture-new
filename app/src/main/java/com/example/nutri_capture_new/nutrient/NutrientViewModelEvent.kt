package com.example.nutri_capture_new.nutrient

import com.example.nutri_capture_new.db.Meal
import java.time.LocalDate

sealed class NutrientViewModelEvent {
    data object InitializeState : NutrientViewModelEvent()
    data object LoadMoreItemsAfterLastDate : NutrientViewModelEvent()
    data object LoadMoreItemsBeforeFirstDate : NutrientViewModelEvent()
    data class InsertMeal(val meal: Meal, val date: LocalDate) : NutrientViewModelEvent()
    data class DeleteMeal(val meal: Meal, val date: LocalDate) : NutrientViewModelEvent()
    data class GetMealsByDate(val date: LocalDate) : NutrientViewModelEvent()
}