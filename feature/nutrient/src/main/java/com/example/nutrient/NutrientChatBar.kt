package com.example.nutrient

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.designsystem.Dimens
import com.example.nutrient.ExpandableTextField

@Composable
fun NutrientChatBar(
    viewModel: NutrientViewModel = hiltViewModel()
) {
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
        ExpandableTextField()

        Spacer(modifier = Modifier.width(4.dp))

        Box(
            modifier = Modifier.height(Dimens.TextField.minHeight), // TextField의 minHeight에 맞췄음
            contentAlignment = Alignment.Center
        ) {
            FilledTonalIconButton(
                onClick = {
                    viewModel.onEvent(
                        NutrientViewModelEvent.InsertInputtedDayMeal
                    )
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

@Composable
fun RowScope.ExpandableTextField(
    viewModel: NutrientViewModel = hiltViewModel()
) {
    val inputtedDayMeal = viewModel.nutrientScreenState.collectAsState().value.inputtedDayMeal

    TextField(
        value = inputtedDayMeal.name,
        onValueChange = { newText ->
            viewModel.onEvent(
                NutrientViewModelEvent.UpdateInputtedDayMeal(
                    inputtedDayMeal.copy(
                        name = newText
                    )
                )
            )
        },
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
}
