package com.example.nutri_capture_new

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nutri_capture_new.ui.theme.NutricapturenewTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NutricapturenewTheme {
                // Navigation 관리의 주체
                val navController = rememberNavController()

                // Snackbar를 위한 CoroutineScope와 State
                val scope = rememberCoroutineScope()
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("Sample Top Bar Text") }
                        )
                    },
                    bottomBar = {
                        BottomAppBar(
                            content = { Text("Sample Bottom Bar Text") }
                        )
                    },
                    snackbarHost = {
                        SnackbarHost(
                            hostState = snackbarHostState,
                            snackbar = { snackbarData ->
                                Snackbar(
                                    modifier = Modifier.padding(12.dp),
                                ) {
                                    Text(
                                        text = snackbarData.visuals.message,
                                    )
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "nutrientInputScreen",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = "nutrientInputScreen") {
                            NutrientInputScreen(
                                scope = scope,
                                snackbarHostState = snackbarHostState
                            )
                        }
                        composable(route = "statisticsScreen") {
                            StatisticsScreen(
                                scope = scope,
                                snackbarHostState = snackbarHostState
                            )
                        }
                        composable(route = "userInfoScreen") {
                            UserInfoScreen(
                                scope = scope,
                                snackbarHostState = snackbarHostState
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SampleContent(
    text: String,
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    Box(
        modifier = modifier
    ) {
        Button(
            onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Button Clicked",
                        duration = SnackbarDuration.Short
                    )
                }
            },
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(text = text)
        }
    }
}