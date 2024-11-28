package com.example.nutri_capture_new.nutrient

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutri_capture_new.db.DayMealView
import com.example.nutri_capture_new.db.MainRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class NutrientViewModel(private val repository: MainRepository) : ViewModel() {
    // (1) 화면 표시용 State
    private val _nutrientScreenState = mutableStateOf(
        NutrientScreenState(
            dayMeals = SnapshotStateList()
        )
    )
    val nutrientScreenState: State<NutrientScreenState>
        get() = _nutrientScreenState

    // (2) ViewModel용 내부 변수
    private val _isInitialized = mutableStateOf(false)
    val isInitialized: State<Boolean>
        get() = _isInitialized

    // (3) View에서 받아 처리할 이벤트
    private val _nutrientScreenEventFlow = MutableSharedFlow<NutrientScreenEvent>()
    val nutrientScreenEventFlow: SharedFlow<NutrientScreenEvent>
        get() = _nutrientScreenEventFlow.asSharedFlow()

    // (4) View로부터 받은 이벤트 처리
    fun onEvent(event: NutrientViewModelEvent) {
        when (event) {
            is NutrientViewModelEvent.InitializeState -> {
                viewModelScope.launch {
                    _nutrientScreenState.value.dayMeals.apply {
                        clear()
                        addAll(repository.getAllDayMeals(10))
                    }
                }
                _isInitialized.value = true
            }

            is NutrientViewModelEvent.LoadMoreItemsAfterLastDayMeal -> {
                viewModelScope.launch {
                    if(_nutrientScreenState.value.dayMeals.isNotEmpty()) {
                        val lastDayMeal = _nutrientScreenState.value.dayMeals.last()
                        _nutrientScreenState.value.dayMeals.addAll(
                            repository.getNextDayMealsAfter(lastDayMeal, 10)
                        )
                    }
                }
            }

            is NutrientViewModelEvent.InsertMeal -> {
                viewModelScope.launch {
                    var insertedMealId = -1L
                    insertedMealId = repository.insertMeal(event.meal, event.date)
                    if (insertedMealId != -1L) {
                        val insertedDayMeal = repository.getDayMeal(insertedMealId)
                        val index =
                            findIndexToInsert(_nutrientScreenState.value.dayMeals, insertedDayMeal)
                        _nutrientScreenState.value.dayMeals.add(index, insertedDayMeal)
                    }
                }
            }

            is NutrientViewModelEvent.DeleteMeal -> {
                viewModelScope.launch {
                    var deletedRowCount = 0
                    deletedRowCount = repository.deleteMeal(event.meal)
                    if (deletedRowCount == 1) {
                        val deletedMealId = event.meal.mealId
                        _nutrientScreenState.value.dayMeals.removeIf { it.mealId == deletedMealId }
                    }
                }
            }

            is NutrientViewModelEvent.DeleteDayMeal -> {
                viewModelScope.launch {
                    var deletedRowCount = 0
                    deletedRowCount = repository.deleteDayMeal(event.dayMeal)
                    if (deletedRowCount == 1) {
                        _nutrientScreenState.value.dayMeals.removeIf { it.mealId == event.dayMeal.mealId }
                    }
                }
            }
        }
    }
}

private fun findIndexToInsert(list: SnapshotStateList<DayMealView>, newItem: DayMealView): Int {
    for(i: Int in list.indices) {
        if(newItem < list[i]) {
            return i
        }
    }
    // 삽입할 위치가 없다면 맨 끝(list.size)에 삽입
    return list.size
}