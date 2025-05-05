package com.raya.challenge.designsystem.component.background

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import com.raya.challenge.designsystem.theme.color.RayaColor

@Composable
fun StyledBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(
                colors = listOf(RayaColor.primary, RayaColor.primary.copy(alpha = 0.9f))
            ))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            val path1 = Path().apply {
                moveTo(0f, height * 0.75f)
                quadraticTo(width * 0.35f, height, width * 0.75f, height * 0.8f)
                quadraticTo(width * 0.95f, height * 0.7f, width, height)
                lineTo(width, height)
                lineTo(0f, height)
                close()
            }
            drawPath(path1, color = Color.White.copy(alpha = 0.1f))

            val path2 = Path().apply {
                moveTo(width, 0f)
                quadraticTo(width * 0.8f, height * 0.2f, width * 0.5f, height * 0.1f)
                quadraticTo(width * 0.15f, 0f, 0f, height * 0.2f)
                lineTo(0f, 0f)
                lineTo(width, 0f)
                close()
            }
            drawPath(path2, color = Color.White.copy(alpha = 0.08f))
        }


        content()
    }
}