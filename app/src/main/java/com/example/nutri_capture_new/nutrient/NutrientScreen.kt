package com.example.nutri_capture_new.nutrient

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun NutrientScreen(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    viewModel: NutrientViewModel = viewModel<NutrientViewModel>(),
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

                is NutrientScreenEvent.ScrollToItem -> {
                    Log.i("interfacer_han", "(이벤트 ScrollToItem) 시작 (아이템 갯수: ${viewModel.nutrientScreenState.value.dailyMeals.size})")
                    listState.scrollToItem(event.index)
                    Log.i("interfacer_han", "(이벤트 ScrollToItem) 끝 (아이템 갯수: ${viewModel.nutrientScreenState.value.dailyMeals.size})")
                }
            }
        }
    }

    LaunchedEffect(key1 = viewModel.isInitialized.value) {
        if(viewModel.isInitialized.value) {
            // 무한 스크롤
            snapshotFlow { listState.layoutInfo.visibleItemsInfo }.collect { visibleItemsInfo ->
                val totalMaxIndex = listState.layoutInfo.totalItemsCount - 1
                val firstVisibleItemIndex = listState.firstVisibleItemIndex
                val visibleItemCount = visibleItemsInfo.size

                if(totalMaxIndex <= firstVisibleItemIndex + visibleItemCount) {
                    viewModel.onEvent(NutrientViewModelEvent.LoadMoreItemsAfterLastDate)
                }

                if(firstVisibleItemIndex == 0) {
                    Log.i("interfacer_han", "(이벤트 LoadMoreItemsBeforeFirstDate) 호출")
                    viewModel.onEvent(NutrientViewModelEvent.LoadMoreItemsBeforeFirstDate)
                }
            }
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        val dailyMeals = viewModel.nutrientScreenState.value.dailyMeals
        Log.i("interfacer_han", "(LazyColumn Recomposition) 아이템 갯수: ${dailyMeals.size}")
        items(dailyMeals) { dailyMeal ->
            Text(
                text = dailyMeal.date.toString(),
                color = Color.White,
                fontSize = 40.sp
            )
        }
    }
}
