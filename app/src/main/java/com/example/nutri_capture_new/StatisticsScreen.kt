package com.example.nutri_capture_new

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.nutri_capture_new.ui.theme.Dimens
import kotlinx.coroutines.CoroutineScope

@Composable
fun StatisticsScreen(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    val nutrientIcons = arrayOf(
        painterResource(id = R.drawable.pan_tool_alt),
        painterResource(id = R.drawable.flour_excess),
        painterResource(id = R.drawable.fiber_quality),
        painterResource(id = R.drawable.sodium_excess),
        painterResource(id = R.drawable.protein_quality),
        painterResource(id = R.drawable.overeating_excess),
        painterResource(id = R.drawable.refined_grain_excess),
        painterResource(id = R.drawable.refined_sugar_excess)
    )

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = Dimens.IconButton.targetSize),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(nutrientIcons) { nutrientIcon ->
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                FilledTonalIconButton(
                    onClick = {

                    },
                    modifier = Modifier
                        .size(Dimens.IconButton.targetSize)
                        .padding((Dimens.IconButton.targetSize - Dimens.IconButton.stateLayer) / 2) // 이 padding() 제거 시, stateLayer는 사라지게 됨 (= stateLayer가 targetSize와 똑같은 크기가 됨)
                        .align(Alignment.Center)
                        //.background(Color.Red),
                ) {
                    Icon(
                        painter = nutrientIcon,
                        contentDescription = "test",
                        modifier = Modifier.size(Dimens.IconButton.iconSize)
                    )
                }
            }
        }
    }
}
