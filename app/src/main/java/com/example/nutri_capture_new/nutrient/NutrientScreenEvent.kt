package com.example.nutri_capture_new.nutrient

sealed class NutrientScreenEvent {
    data class ShowSnackbar(val message: String) : NutrientScreenEvent()
}