package com.example.nutri_capture_new.utils

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ResponsiveArcSurroundedIconButton(
    imageVector: ImageVector,
    contentDescription: String,
    currentLevel: Int,
    maxLevel: Int,
    arcColor: Color,
    arcWidth: Int,
    onIconClick: () -> Unit
) {
    Box(
        modifier = Modifier.size((40 + arcWidth).dp),
        contentAlignment = Alignment.Center
    ) {
        // 레이어 1
        val animatedCurrentLevel by animateFloatAsState(
            targetValue = currentLevel.toFloat(),
            animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
        )

        Canvas(modifier = Modifier.fillMaxSize()) {
            animatedDrawArc(
                currentLevel = animatedCurrentLevel,
                maxLevel = maxLevel,
                color = arcColor
            )
        }

        // 레이어 2
        FilledTonalIconButton(
            onClick = {
                onIconClick()
            }
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription
            )
        }
    }
}

private fun DrawScope.animatedDrawArc(
    currentLevel: Float, maxLevel: Int, color: Color
) {
    val radius = (size.minDimension / 2) // minDimension은 size의 너비와 높이 중 더 작은 값을 반환
    val center = Offset(size.width / 2, size.height / 2) // 맨 ↖에서 →쪽으로 (너비/2)만큼 ↓쪽으로 (높이/2)만큼 움직인 곳
    val anglePerLevel = 360f / maxLevel

    drawArc(
        color = color,
        startAngle = 180f, // 시침의 3시 방향부터 시작
        sweepAngle = anglePerLevel * currentLevel,
        useCenter = true,
        topLeft = Offset(center.x - radius, center.y - radius),
        size = Size(radius * 2, radius * 2)
    )
}