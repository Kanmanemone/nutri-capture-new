package com.example.nutri_capture_new.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

object Dimens {

    object ChatBar {
        val minHeight = 80.dp
        val maxHeight = 200.dp
        val paddingTop = 12.dp
        val paddingBottom = 12.dp
        val paddingStart = 12.dp
        val paddingEnd = 12.dp
    }

    object TextField {
        val minHeight = 56.dp
        val maxHeight = 200.dp
        val roundedCorner = minHeight / 2

        @Composable
        fun textStyle(): TextStyle { // MaterialTheme.typography는 @Composable 함수에서만 접근 가능
            return MaterialTheme.typography.bodyLarge
        }
    }

    object IconButton {
        val iconSize = 24.dp
        val stateLayer = 40.dp
        val targetSize = 48.dp
    }
}