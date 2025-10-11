package com.example.nutri_capture_new

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.designsystem.NutricapturenewTheme
import com.example.nutrient.NutrientBottomSheet
import com.example.nutrient.NutrientChatBar
import com.example.nutrient.NutrientScreen
import com.example.nutrient.NutrientViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // nutrientViewModel이 Hilt의 viewModels()에 의해 관리되도록 위임(by). 따라서 반드시 by 키워드로 선언해야함.
    private val nutrientViewModel: NutrientViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            NutricapturenewTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.systemBars)
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .windowInsetsPadding(WindowInsets.ime),
                ) {
                    BottomSheetScaffold(
                        sheetContent = {
                            NutrientBottomSheet()
                        },
                        modifier = Modifier.fillMaxSize(),
                        sheetPeekHeight = 300.dp
                    ) { innerPadding ->
                        Column(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize()
                        ) {
                            NutrientScreen()
                            NutrientChatBar()
                        }
                    }
                }
            }
        }
    }
}