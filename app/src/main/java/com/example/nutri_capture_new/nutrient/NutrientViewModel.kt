package com.example.nutri_capture_new.nutrient

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.time.LocalDate

class NutrientViewModel : ViewModel() {
    // (1) 화면 표시용 State
    private val _nutrientScreenState = mutableStateOf(
        NutrientScreenState(
            dailyMeals = SnapshotStateList()
        )
    )
    val nutrientScreenState: State<NutrientScreenState>
        get() = _nutrientScreenState

    // (2) View에서 받아 처리할 이벤트
    private val _nutrientScreenEventFlow = MutableSharedFlow<NutrientScreenEvent>()
    val nutrientScreenEventFlow: SharedFlow<NutrientScreenEvent>
        get() = _nutrientScreenEventFlow.asSharedFlow()

    // (3) View로부터 받은 이벤트 처리
    fun onEvent(event: NutrientViewModelEvent) {
        when (event) {
            is NutrientViewModelEvent.InitializeState -> {
                _nutrientScreenState.value.dailyMeals.add(
                    DailyMeal(
                        date = LocalDate.now(),
                        meals = SnapshotStateList()
                    )
                )
            }

            is NutrientViewModelEvent.LoadMoreItemsAfterLastDate -> {
                val lastDate = _nutrientScreenState.value.dailyMeals.last().date
                _nutrientScreenState.value.dailyMeals.add(
                    DailyMeal(
                        date = lastDate.plusDays(1),
                        meals = SnapshotStateList()
                    )
                )
            }

            is NutrientViewModelEvent.LoadMoreItemsBeforeFirstDate -> {
                val firstDate = _nutrientScreenState.value.dailyMeals.first().date
                _nutrientScreenState.value.dailyMeals.add(
                    0,
                    DailyMeal(
                        date = firstDate.minusDays(1),
                        meals = SnapshotStateList()
                    )
                )
            }
        }
    }
}