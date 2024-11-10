package com.example.nutri_capture_new.nutrient

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutri_capture_new.db.MainRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class NutrientViewModel(private val repository: MainRepository) : ViewModel() {
    // (1) 화면 표시용 State
    private val _nutrientScreenState = mutableStateOf(
        NutrientScreenState(
            listOfDateAndMeals = SnapshotStateList()
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
                var dateToInsert = LocalDate.now()
                repeat(20) {
                    _nutrientScreenState.value.listOfDateAndMeals.add(
                        DateAndMeals(
                            date = dateToInsert,
                            meals = SnapshotStateList()
                        )
                    )
                    dateToInsert = dateToInsert.plusDays(1)
                }

                _isInitialized.value = true
            }

            is NutrientViewModelEvent.LoadMoreItemsAfterLastDate -> {
                val lastDate = _nutrientScreenState.value.listOfDateAndMeals.last().date
                _nutrientScreenState.value.listOfDateAndMeals.add(
                    DateAndMeals(
                        date = lastDate.plusDays(1),
                        meals = SnapshotStateList()
                    )
                )
            }

            is NutrientViewModelEvent.LoadMoreItemsBeforeFirstDate -> {
                val firstDate = _nutrientScreenState.value.listOfDateAndMeals.first().date
                _nutrientScreenState.value.listOfDateAndMeals.add(
                    0,
                    DateAndMeals(
                        date = firstDate.minusDays(1),
                        meals = SnapshotStateList()
                    )
                )
            }

            is NutrientViewModelEvent.InsertMeal -> {
                viewModelScope.launch {
                    var insertedMealId = 0L
                    insertedMealId = repository.insertMeal(event.meal, event.date)
                    if (insertedMealId != 0L) {
                        val dateAndMealsForUpdate =
                            _nutrientScreenState.value.listOfDateAndMeals.find { it.date == event.date }!!
                        dateAndMealsForUpdate.meals.add(event.meal)
                    }
                }
            }

            is NutrientViewModelEvent.DeleteMeal -> {
                viewModelScope.launch {
                    var deletedRowCount = 0
                    deletedRowCount = repository.deleteMeal(event.meal)
                    if (deletedRowCount == 1) {
                        val dateAndMealsForUpdate =
                            _nutrientScreenState.value.listOfDateAndMeals.find { it.date == event.date }!!
                        val mealForDelete =
                            dateAndMealsForUpdate.meals.find { it.mealId == event.meal.mealId }!!
                        dateAndMealsForUpdate.meals.remove(mealForDelete)
                    }
                }
            }

            is NutrientViewModelEvent.GetMealsByDate -> {
                viewModelScope.launch {
                    val mealForUpdate =
                        _nutrientScreenState.value.listOfDateAndMeals.find { it.date == event.date }!!.meals
                    mealForUpdate.clear()
                    mealForUpdate.addAll(repository.getMealsOrderedByTime(event.date))
                }
            }
        }
    }

    // (5) DayMealView 작동 확인용 로그
    fun log() {
        viewModelScope.launch {
            Log.i("interfacer_han", repository.getAllMeals().toString())
        }
    }
}