package com.example.nutri_capture_new.nutrient

sealed class NutrientViewModelEvent {
    data object InitializeState : NutrientViewModelEvent()
}