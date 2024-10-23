package com.example.nutri_capture_new.nutrient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nutri_capture_new.db.MainRepository

class NutrientViewModelFactory(private val repository: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NutrientViewModel::class.java)) {
            return NutrientViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}