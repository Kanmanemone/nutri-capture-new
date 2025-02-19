package com.example.nutri_capture_new.nutrient

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NutrientBottomSheet(
    viewModel: NutrientViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SampleTexts()
    }
}

@Composable
fun SampleTexts(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 16.sp,
) {
    val textCount = 30
    repeat(textCount) {
        Text(
            text = "Item (${it + 1} / ${textCount})",
            modifier = modifier,
            fontSize = fontSize,
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}