package com.example.nutri_capture_new

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope

@Composable
fun UserInfoScreen(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SampleContent(
            text = "UserInfoScreen",
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray),
            scope = scope,
            snackbarHostState = snackbarHostState
        )
    }
}