package com.example.nutrient

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.common.DateFormatter

@Composable
fun ColumnScope.NutrientHistory(
    viewModel: NutrientViewModel = hiltViewModel()
) {
    LazyColumn(
        modifier = Modifier.weight(1f),
        reverseLayout = true
    ) {
        item { // 이 1dp 높이의 item이 없으면, 새 아이템 생성 시의, '생성 애니메이션'이 잘 안 뜸
            Spacer(modifier = Modifier.height(1.dp))
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
                            text = "name: ${dayMeal.name} mealId: ${dayMeal.mealId}",
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