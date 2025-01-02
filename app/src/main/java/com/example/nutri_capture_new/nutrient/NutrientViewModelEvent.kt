package com.example.nutri_capture_new.nutrient

import com.example.nutri_capture_new.db.DayMealView
import com.example.nutri_capture_new.db.Meal
import java.time.LocalDate

sealed class NutrientViewModelEvent {
    data class InsertMeal(val meal: Meal, val date: LocalDate) : NutrientViewModelEvent()
    data class DeleteMeal(val meal: Meal) : NutrientViewModelEvent()
    data class DeleteDayMeal(val dayMeal: DayMealView) : NutrientViewModelEvent()
}