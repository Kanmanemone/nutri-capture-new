package com.example.nutri_capture_new

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nutri_capture_new.db.MainDatabase
import com.example.nutri_capture_new.db.MainRepository
import com.example.nutri_capture_new.nutrient.NutrientScreen
import com.example.nutri_capture_new.nutrient.NutrientViewModelFactory
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
                val navBackStackEntry = navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry.value?.destination?.route

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
                        when(currentRoute) {
                            Destination.NutrientScreen.route -> NutrientBottomBar()
                            else -> MainNavigationBar(navController)
                        }
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
                        startDestination = Destination.NutrientScreen.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = Destination.NutrientScreen.route) {
                            val dao = MainDatabase.getInstance(application).mainDAO
                            val repository = MainRepository(dao)
                            NutrientScreen(
                                scope = scope,
                                snackbarHostState = snackbarHostState,
                                viewModel = viewModel(factory = NutrientViewModelFactory(repository))
                            )
                        }
                        composable(route = Destination.StatisticsScreen.route) {
                            StatisticsScreen(
                                scope = scope,
                                snackbarHostState = snackbarHostState
                            )
                        }
                        composable(route = Destination.UserInfoScreen.route) {
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

    @Composable
    private fun MainNavigationBar(navController: NavHostController) {
        val items = listOf(
            Destination.NutrientScreen,
            Destination.StatisticsScreen,
            Destination.UserInfoScreen
        )

        NavigationBar {
            val currentRoute = navController.currentDestination?.route

            items.forEach { item ->
                NavigationBarItem(
                    selected = (item.route == currentRoute),
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            painterResource(id = item.iconId),
                            contentDescription = item.title
                        )
                    }
                )
            }
        }
    }

    @Composable
    private fun NutrientBottomBar() {
        BottomAppBar {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 2.dp,
                        start = 8.dp,
                        bottom = 6.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                val inputtedText = remember { mutableStateOf("") }

                OutlinedTextField(
                    value = inputtedText.value,
                    onValueChange = { newText -> inputtedText.value = newText },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    textStyle = TextStyle(
                        fontSize = 16.sp
                    ),
                    placeholder = { Text("메시지 입력") },
                    shape = RoundedCornerShape(50)
                )

                IconButton(
                    onClick = { },
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

sealed class Destination(val route: String, val title: String, val iconId: Int) {
    data object NutrientScreen : Destination("nutrientScreen", "캡처", R.drawable.pan_tool_alt)
    data object StatisticsScreen : Destination("statisticsScreen", "통계", R.drawable.stacked_line_chart)
    data object UserInfoScreen : Destination("userInfoScreen", "내 정보", R.drawable.manage_accounts)
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