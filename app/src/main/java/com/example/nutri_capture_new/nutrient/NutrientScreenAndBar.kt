package com.example.nutri_capture_new.nutrient

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nutri_capture_new.db.Meal
import com.example.nutri_capture_new.db.NutritionInfo
import com.example.nutri_capture_new.ui.theme.Dimens
import com.example.nutri_capture_new.utils.DateFormatter
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun NutrientScreen(
    viewModel: NutrientViewModel,
    listState: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
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
        var inputtedText by remember { mutableStateOf("") }
        TextField(
            value = inputtedText,
            onValueChange = { newText -> inputtedText = newText },
            modifier = Modifier
                .weight(1f)
                .heightIn(min = Dimens.TextField.minHeight, max = Dimens.TextField.maxHeight)
                .padding() // 이 TextField()를 감싸는 Row()의 padding이, TextField의 padding 역할을 대신 수행
                .clip(shape = RoundedCornerShape(Dimens.TextField.roundedCorner)),
            textStyle = Dimens.TextField.textStyle(),
            placeholder = {
                Text(
                    text = "메뉴 입력",
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
                                name = inputtedText,
                                nutritionInfo = NutritionInfo()
                            ),
                            date = LocalDate.now()
                        )
                    )
                    inputtedText = ""
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