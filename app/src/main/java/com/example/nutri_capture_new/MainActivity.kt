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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutri_capture_new.db.MainRepository
import com.example.nutri_capture_new.nutrient.NutrientChatBar
import com.example.nutri_capture_new.nutrient.NutrientScreen
import com.example.nutri_capture_new.nutrient.NutrientViewModel
import com.example.nutri_capture_new.ui.theme.NutricapturenewTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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
                BottomSheetScaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.ime),
                    sheetContent = {

                    }
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