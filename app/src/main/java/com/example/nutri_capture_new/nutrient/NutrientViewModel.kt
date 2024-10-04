package com.example.nutri_capture_new.nutrient

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.time.LocalDate

class NutrientViewModel : ViewModel() {
    // (1) 화면 표시용 State
    private val _nutrientScreenState = mutableStateOf(
        NutrientScreenState(
            dailyMeals = emptyList()
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
                _nutrientScreenState.value = _nutrientScreenState.value.copy(
                    dailyMeals = listOf(
                        DailyMeal(
                            date = LocalDate.of(2011, 11, 11),
                            meals = emptyList()
                        ),
                        DailyMeal(
                            date = LocalDate.of(2011, 11, 12),
                            meals = emptyList()
                        ),
                        DailyMeal(
                            date = LocalDate.of(2011, 11, 13),
                            meals = emptyList()
                        ),
                        DailyMeal(
                            date = LocalDate.of(2011, 11, 14),
                            meals = emptyList()
                        ),
                        DailyMeal(
                            date = LocalDate.of(2011, 11, 15),
                            meals = emptyList()
                        )
                    )
                )
            }
        }
    }
}