package com.example.nutri_capture_new

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutri_capture_new.db.MainDAO
import com.example.nutri_capture_new.db.MainDatabase
import com.example.nutri_capture_new.db.MainRepository
import com.example.nutri_capture_new.nutrient.NutrientChatBar
import com.example.nutri_capture_new.nutrient.NutrientScreen
import com.example.nutri_capture_new.nutrient.NutrientViewModelFactory
import com.example.nutri_capture_new.ui.theme.NutricapturenewTheme

class MainActivity : ComponentActivity() {

    private lateinit var dao: MainDAO
    private lateinit var repository: MainRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dao = MainDatabase.getInstance(application).mainDAO
        repository = MainRepository(dao)

        enableEdgeToEdge()
        setContent {
            NutricapturenewTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NutrientChatBar(
                            viewModel(
                                factory = NutrientViewModelFactory(repository)
                            )
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        NutrientScreen(
                            viewModel = viewModel(factory = NutrientViewModelFactory(repository))
                        )
                    }
                }
            }
        }
    }
}