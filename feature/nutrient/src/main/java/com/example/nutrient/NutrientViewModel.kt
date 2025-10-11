package com.example.nutrient

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.database.DayMeal
import com.example.database.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutrientViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {
    // 화면 표시용 State
    private val _nutrientScreenState = MutableStateFlow(
        NutrientScreenState(
            dayMeals = SnapshotStateList(),
            inputtedDayMeal = DayMeal()
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

    // View로부터 받은 이벤트 처리
    fun onEvent(event: NutrientViewModelEvent) {
        when (event) {
            is NutrientViewModelEvent.UpdateInputtedDayMeal -> {
                _nutrientScreenState.value = _nutrientScreenState.value.copy(
                    inputtedDayMeal = event.dayMeal
                )
            }

            is NutrientViewModelEvent.InsertInputtedDayMeal -> {
                viewModelScope.launch {
                    repository.insertDayMeal(_nutrientScreenState.value.inputtedDayMeal)
                    _nutrientScreenState.value = _nutrientScreenState.value.copy(
                        inputtedDayMeal = DayMeal()
                    )
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