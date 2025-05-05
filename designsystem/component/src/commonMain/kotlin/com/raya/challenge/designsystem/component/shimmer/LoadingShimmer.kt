package com.raya.challenge.designsystem.component.shimmer

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

fun Modifier.hasShimmerEffect(
    isLoading: Boolean,
    shape: Shape = RoundedCornerShape(10.dp)
): Modifier = this.run {
    if (isLoading)
        this.shimmerLoadingAnimation()
            .clip(shape)
            .drawWithContent {
                drawRoundRect(
                    color = Color(0xFFE2DFE8),
                )
            }
    else
        this
}

fun Modifier.shimmer() = hasShimmerEffect(isLoading = true)