package com.example.nutri_capture_new.nutrient

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nutri_capture_new.utils.ResponsiveArcSurroundedIconButton

@Composable
fun NutrientBottomSheet(
    viewModel: NutrientViewModel = hiltViewModel()
) {
    val inputtedDayMeal = viewModel.nutrientScreenState.collectAsState().value.inputtedDayMeal
    val nutritionInfo = inputtedDayMeal.nutritionInfo
    val maxLevel = 5

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 100.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items(
            nutritionInfo.toMutableMap().toList()
        ) { nutritionDetailMap ->
            val nutritionKey = nutritionDetailMap.first
            val nutritionDetail = nutritionDetailMap.second

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .border(width = 1.dp, color = Color.Black),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ResponsiveArcSurroundedIconButton(
                    imageVector = ImageVector.vectorResource(id = nutritionDetail.iconId),
                    contentDescription = nutritionDetail.name,
                    currentLevel = nutritionDetail.value,
                    maxLevel = maxLevel,
                    arcColor = Color.Yellow,
                    arcWidth = 15
                ) {
                    viewModel.onEvent(
                        NutrientViewModelEvent.UpdateInputtedDayMeal(
                            inputtedDayMeal.copy(
                                nutritionInfo = nutritionInfo.updateNutritionDetail(nutritionKey) {
                                    if (nutritionDetail.value < maxLevel) {
                                        nutritionDetail.value + 1
                                    } else {
                                        0
                                    }
                                }
                            )
                        )
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = nutritionDetail.name,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}