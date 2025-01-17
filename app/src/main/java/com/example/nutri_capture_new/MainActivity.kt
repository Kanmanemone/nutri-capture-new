package com.example.nutri_capture_new

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nutri_capture_new.db.MainDAO
import com.example.nutri_capture_new.db.MainDatabase
import com.example.nutri_capture_new.db.MainRepository
import com.example.nutri_capture_new.db.Meal
import com.example.nutri_capture_new.db.NutritionInfo
import com.example.nutri_capture_new.nutrient.NutrientScreen
import com.example.nutri_capture_new.nutrient.NutrientViewModel
import com.example.nutri_capture_new.nutrient.NutrientViewModelEvent
import com.example.nutri_capture_new.nutrient.NutrientViewModelFactory
import com.example.nutri_capture_new.ui.theme.Dimens
import com.example.nutri_capture_new.ui.theme.NutricapturenewTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class MainActivity : ComponentActivity() {

    private lateinit var dao: MainDAO
    private lateinit var repository: MainRepository

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dao = MainDatabase.getInstance(application).mainDAO
        repository = MainRepository(dao)

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
                            title = {
                                Text(
                                    text = "Sample Top Bar Text",
                                    style = MaterialTheme.typography.headlineLarge
                                )
                            },
                            actions = {
                                IconButton(
                                    onClick = {
                                        navController.navigate("statisticsScreen") {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                ) {
                                    Icon(
                                        painterResource(id = R.drawable.stacked_line_chart),
                                        contentDescription = "통계"
                                    )
                                }
                            }
                        )
                    },
                    bottomBar = {
                        when (currentRoute) {
                            Destination.NutrientScreen.route -> NutrientChatBar(
                                viewModel(
                                    factory = NutrientViewModelFactory(repository)
                                )
                            )

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
                                        style = MaterialTheme.typography.bodyLarge,
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
    fun NutrientChatBar(viewModel: NutrientViewModel) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = Dimens.ChatBar.minHeight, max = Dimens.ChatBar.maxHeight)
                .navigationBarsPadding() // 없으면 이 컴포저블이 시스템 네비게이션 바 가림
                .padding(
                    start = Dimens.ChatBar.paddingStart,
                    top = Dimens.ChatBar.paddingTop,
                    end = Dimens.ChatBar.paddingEnd,
                    bottom = Dimens.ChatBar.paddingBottom
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            val inputtedText = remember { mutableStateOf("") }
            TextField(
                value = inputtedText.value,
                onValueChange = { newText -> inputtedText.value = newText },
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = Dimens.TextField.minHeight, max = Dimens.TextField.maxHeight)
                    .padding() // 이 TextField()를 감싸는 Row()의 padding이, TextField의 padding 역할을 대신 수행
                    .clip(shape = RoundedCornerShape(Dimens.TextField.roundedCorner)),
                textStyle = Dimens.TextField.textStyle(),
                placeholder = {
                    Text(
                        text = "메시지 입력",
                        style = Dimens.TextField.textStyle()
                    )
                },
                colors = TextFieldDefaults.colors().copy(
                    unfocusedIndicatorColor = Color.Transparent, // 맨 하단에 있는 밑줄 투명화
                    focusedIndicatorColor = Color.Transparent // TextField가 포커스될 때 맨 하단 밑줄 색 투명화
                )
            )

            Spacer(modifier = Modifier.width(4.dp))

            Box(
                modifier = Modifier.height(Dimens.TextField.minHeight), // TextField의 minHeight에 맞췄음
                contentAlignment = Alignment.Center
            ) {
                FilledTonalIconButton(
                    onClick = {
                        viewModel.onEvent(
                            NutrientViewModelEvent.InsertMeal(
                                meal = Meal(
                                    time = LocalTime.now(),
                                    name = inputtedText.value,
                                    nutritionInfo = NutritionInfo()
                                ),
                                date = LocalDate.now()
                            )
                        )
                        inputtedText.value = ""
                    },
                    modifier = Modifier
                        .size(Dimens.IconButton.targetSize)
                        .padding((Dimens.IconButton.targetSize - Dimens.IconButton.stateLayer) / 2) // 이 padding() 제거 시, stateLayer는 사라지게 됨 (= stateLayer가 targetSize와 똑같은 크기가 됨)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "전송",
                        modifier = Modifier.size(Dimens.IconButton.iconSize)
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
            Text(
                text = text,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}