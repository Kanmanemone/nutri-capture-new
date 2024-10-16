package com.example.nutri_capture_new.nutrient

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutri_capture_new.SampleContent
import com.example.nutri_capture_new.utils.DateFormatter
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
            }
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
                    viewModel.onEvent(NutrientViewModelEvent.LoadMoreItemsAfterLastDate)
                }

                if (firstVisibleItemIndex == 0) {
                    viewModel.onEvent(NutrientViewModelEvent.LoadMoreItemsBeforeFirstDate)
                    // 역방향 무한 스크롤 구현을 위한 암시적 스크롤
                    listState.requestScrollToItem(1, listState.firstVisibleItemScrollOffset)
                }
            }
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize()
    ) {
        val dailyMeals = viewModel.nutrientScreenState.value.dailyMeals
        items(dailyMeals) { dailyMeal ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 8.dp,
                        top = 8.dp,
                        end = 8.dp,
                    ),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = DateFormatter.formatDateForNutrientScreen(dailyMeal.date),
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 15.sp,
                        textAlign = TextAlign.End
                    )

                    SampleContent(
                        text = "Sample 영양 정보",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        scope = scope,
                        snackbarHostState = snackbarHostState
                    )
                }
            }
        }
    }
}
