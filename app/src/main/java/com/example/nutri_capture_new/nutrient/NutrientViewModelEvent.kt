package com.example.nutri_capture_new.nutrient

sealed class NutrientViewModelEvent {
    data object InitializeState : NutrientViewModelEvent()
    data object LoadMoreItemsAfterLastDate : NutrientViewModelEvent()
    data object LoadMoreItemsBeforeFirstDate : NutrientViewModelEvent()
}