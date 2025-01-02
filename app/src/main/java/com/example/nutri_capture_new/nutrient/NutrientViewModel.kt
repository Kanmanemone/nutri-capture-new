package com.example.nutri_capture_new.nutrient

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutri_capture_new.db.MainRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class NutrientViewModel(private val repository: MainRepository) : ViewModel() {
    // (1) 화면 표시용 State
    private val _nutrientScreenState = MutableStateFlow(
        NutrientScreenState(
            dayMeals = SnapshotStateList()
        )
    )
    val nutrientScreenState: StateFlow<NutrientScreenState>
        get() = _nutrientScreenState

    init {
        viewModelScope.launch {
            repository.getAllDayMeals().collect { dayMeals ->
                _nutrientScreenState.value.dayMeals.apply {
                    clear()
                    addAll(dayMeals)
                }
            }
        }
    }

    // (2) View에서 받아 처리할 이벤트
    private val _nutrientScreenEventFlow = MutableSharedFlow<NutrientScreenEvent>()
    val nutrientScreenEventFlow: SharedFlow<NutrientScreenEvent>
        get() = _nutrientScreenEventFlow.asSharedFlow()

    // (3) View로부터 받은 이벤트 처리
    fun onEvent(event: NutrientViewModelEvent) {
        when (event) {
            is NutrientViewModelEvent.InsertMeal -> {
                viewModelScope.launch {
                    repository.insertMeal(event.meal, event.date)
                }
            }

            is NutrientViewModelEvent.DeleteMeal -> {
                viewModelScope.launch {
                    repository.deleteMeal(event.meal)
                }
            }

            is NutrientViewModelEvent.DeleteDayMeal -> {
                viewModelScope.launch {
                    repository.deleteDayMeal(event.dayMeal)
                }
            }
        }
    }
}