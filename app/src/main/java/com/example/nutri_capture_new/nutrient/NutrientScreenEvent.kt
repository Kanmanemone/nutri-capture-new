package com.example.nutri_capture_new.nutrient

sealed class NutrientScreenEvent {
    data class ShowSnackbar(val message: String) : NutrientScreenEvent()
    data class ScrollToItem(val index: Int) : NutrientScreenEvent()
}