package com.example.nutri_capture_new.nutrient

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nutri_capture_new.db.DayMeal
import com.example.nutri_capture_new.db.NutritionInfo
import com.example.nutri_capture_new.utils.ResponsiveArcSurroundedIconButton

@Composable
fun NutrientBottomSheet(
    viewModel: NutrientViewModel = hiltViewModel()
) {
    val inputtedDayMeal = viewModel.nutrientScreenState.collectAsState().value.inputtedDayMeal

    NutrientInputKeyboard(
        modifier = Modifier.fillMaxSize(), inputtedDayMeal = inputtedDayMeal
    )
}

@Composable
fun NutrientInputKeyboard(
    modifier: Modifier = Modifier,
    inputtedDayMeal: DayMeal,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 100.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items(
            11 // TODO: inputtedDayMeal.nutritionInfo의 각 프로퍼티만큼 item 생성
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(width = 1.dp, color = Color.Black),
                contentAlignment = Alignment.Center
            ) {
                ResponsiveArcSurroundedIconButton(
                    imageVector = Icons.Sharp.Done,
                    contentDescription = "test",
                    currentLevel = 2,
                    maxLevel = 3,
                    arcColor = Color.Yellow,
                    arcWidth = 15
                ) {
                    // TODO: inputtedDayMeal의 값을 변경하는 event
                }
            }
        }
    }
}