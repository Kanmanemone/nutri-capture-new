package com.example.nutri_capture_new.nutrient

import java.time.LocalDate
import java.time.LocalTime

data class NutrientScreenState(
    val dailyMeals: List<DailyMeal>
)

data class DailyMeal(
    val date: LocalDate,
    val meals: List<Meal>
)

data class Meal(
    val time: LocalTime,
    val name: String,
    val nutritionInfo: NutritionInfo,
)