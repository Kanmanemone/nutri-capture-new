package com.example.nutri_capture_new.nutrient

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutri_capture_new.db.Meal
import com.example.nutri_capture_new.db.NutritionInfo
import com.example.nutri_capture_new.utils.DateFormatter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun NutrientScreen(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    viewModel: NutrientViewModel,
    listState: LazyListState = rememberLazyListState()
) {
    LaunchedEffect(key1 = true) {
        // DayMealView 작동 확인용 로그
        viewModel.log()

        // State 초기화
        viewModel.onEvent(NutrientViewModelEvent.InitializeState)

        // ViewModel로부터 받은 이벤트 처리
        viewModel.nutrientScreenEventFlow.collectLatest { event ->
            when (event) {
                is NutrientScreenEvent.ShowSnackbar -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = event.message,
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        repeat(5) {
            viewModel.onEvent(
                NutrientViewModelEvent.InsertMeal(
                    meal = Meal(
                        time = LocalTime.now(),
                        name = "test",
                        nutritionInfo = NutritionInfo()
                    ),
                    date = LocalDate.now()
                )
            )
        }
    }

    LaunchedEffect(key1 = viewModel.isInitialized.value) {
        if (viewModel.isInitialized.value) {
            // 무한 스크롤
            snapshotFlow { listState.layoutInfo.visibleItemsInfo }.collect { visibleItemsInfo ->
                val totalMaxIndex = listState.layoutInfo.totalItemsCount - 1
                val firstVisibleItemIndex = listState.firstVisibleItemIndex
                val visibleItemCount = visibleItemsInfo.size

                if (totalMaxIndex <= firstVisibleItemIndex + visibleItemCount) {
                    viewModel.onEvent(NutrientViewModelEvent.LoadMoreItemsAfterLastDayMeal)
                }
            }
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        reverseLayout = true
    ) {
        val dayMeals = viewModel.nutrientScreenState.value.dayMeals
        itemsIndexed(dayMeals) { index, dayMeal ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 8.dp,
                        top = if (index == dayMeals.lastIndex) 8.dp else 0.dp,
                        end = 8.dp,
                        bottom = 8.dp
                    ),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    Text(
                        text = DateFormatter.formatDateForNutrientScreen(dayMeal.date) + " " + dayMeal.time,
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 15.sp,
                        textAlign = TextAlign.End
                    )

                    Text(
                        text = "mealId: ${dayMeal.mealId}, index: $index",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
