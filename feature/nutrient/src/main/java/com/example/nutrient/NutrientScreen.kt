package com.example.nutrient

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.ui.ContentWithSwipeableBottomSheet
import com.example.ui.rememberContentWithSwipeableBottomSheetState
import com.example.ui.storedImeMaxHeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutrientScreen() {
    val sheetState = rememberContentWithSwipeableBottomSheetState(
        anchoredDraggableState = AnchoredDraggableState(SheetValue.PartiallyExpanded)
    )

    var sheetExpandedAnchorOffset by remember { mutableStateOf(0.dp) }
    val statusBarHeightDp = with(LocalDensity.current) {
        WindowInsets.statusBars.getTop(this).toDp()
    }
    LaunchedEffect(statusBarHeightDp) {
        sheetExpandedAnchorOffset = statusBarHeightDp
    }

    ContentWithSwipeableBottomSheet(
        sheetContent = {
            NutrientBottomSheet()
        },
        modifier = Modifier
            .fillMaxSize()
            .background(BottomAppBarDefaults.containerColor),
        sheetState = sheetState,
        sheetExpandedAnchorOffset = sheetExpandedAnchorOffset,
        sheetPartiallyExpandedHeight = storedImeMaxHeight(),
        sheetSwipeEnabled = true,
        hasContentNavigationBarPadding = true
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            NutrientHistory()
            NutrientChatBar()
        }
    }
}