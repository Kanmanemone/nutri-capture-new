package com.example.nutri_capture_new.nutrient

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
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

    LaunchedEffect(key1 = viewModel.isInitialized.value) {
        if (viewModel.isInitialized.value) {
            // 무한 스크롤
            val shouldLoadMoreData = snapshotFlow {
                val totalMaxIndex = listState.layoutInfo.totalItemsCount - 1
                val firstVisibleItemIndex = listState.firstVisibleItemIndex
                val visibleItemCount = listState.layoutInfo.visibleItemsInfo.size
                totalMaxIndex <= firstVisibleItemIndex + visibleItemCount
            }

            val totalItemCount = snapshotFlow {
                listState.layoutInfo.totalItemsCount
            }

            val loadMoreData = combine(
                shouldLoadMoreData,
                totalItemCount
            ) { shouldLoadMoreDataValue, totalItemCountValue ->
                Pair(shouldLoadMoreDataValue, totalItemCountValue)
            }

            loadMoreData.collect { (shouldLoadMoreData, _) ->
                if (shouldLoadMoreData) {
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
        item {
            FilledTonalButton(
                onClick = {
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
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 8.dp,
                        top = 0.dp,
                        end = 8.dp,
                        bottom = 8.dp
                    ),
                shape = CardDefaults.shape,
                elevation = ButtonDefaults.filledTonalButtonElevation(4.dp)
            ) {
                Text(
                    text = "+",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }

        val dayMeals = viewModel.nutrientScreenState.value.dayMeals
        items(dayMeals, key = { it.mealId }) { dayMeal ->
            val key = remember { dayMeal.mealId }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 8.dp,
                        top = if (key == dayMeals.last().mealId) 8.dp else 0.dp,
                        end = 8.dp,
                        bottom = 8.dp
                    )
                    .animateItem(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = DateFormatter.formatDateForNutrientScreen(dayMeal.date) + " " + dayMeal.time,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                            style = MaterialTheme.typography.labelLarge
                        )

                        Text(
                            text = "mealId: ${dayMeal.mealId}",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }

                    IconButton(
                        onClick = {
                            viewModel.onEvent(
                                NutrientViewModelEvent.DeleteDayMeal(
                                    dayMeal = dayMeal
                                )
                            )
                        },
                        modifier = Modifier.align(Alignment.TopStart)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}