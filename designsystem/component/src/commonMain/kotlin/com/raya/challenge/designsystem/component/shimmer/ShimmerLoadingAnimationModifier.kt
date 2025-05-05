package com.raya.challenge.designsystem.component.shimmer

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Default values for [Modifier.shimmerLoadingAnimation]
 */
object ShimmerLoadingAnimationDefaults {
    val WidthOfShadowBrush: Dp = 320.dp
    const val SHIMMER_MAX_ALPHA: Float = 0.8f
    const val ANGLE_OF_AXIS_Y: Float = 0f
    const val DURATION_MILLIS: Int = 1200
}

/**
 * Modifier which adds a shimmer animation on skeletons.
 * It draws the shimmer effect over any composable, so you can apply this
 * to any Divider/Box/Column/Row/etc.
 * @param widthOfShadowBrush Width of the shimmer effect.
 * Defaults to [ShimmerLoadingAnimationDefaults.WidthOfShadowBrush]
 * @param shimmerMaxAlpha Maximum alpha (opacity) of the shimmer effect. Must be between 0 and 1.
 * Defaults to [ShimmerLoadingAnimationDefaults.SHIMMER_MAX_ALPHA]
 * @param angleOfAxisY The angle of the shimmer effect.
 * Defaults to [ShimmerLoadingAnimationDefaults.ANGLE_OF_AXIS_Y]
 * @param durationMillis Duration of time it takes for the animation to move from left to right,
 * in milliseconds. Defaults to [ShimmerLoadingAnimationDefaults.DURATION_MILLIS]
 */
fun Modifier.shimmerLoadingAnimation(
    widthOfShadowBrush: Dp = ShimmerLoadingAnimationDefaults.WidthOfShadowBrush,
    shimmerMaxAlpha: Float = ShimmerLoadingAnimationDefaults.SHIMMER_MAX_ALPHA,
    angleOfAxisY: Float = ShimmerLoadingAnimationDefaults.ANGLE_OF_AXIS_Y,
    durationMillis: Int = ShimmerLoadingAnimationDefaults.DURATION_MILLIS,
): Modifier {
    return composed {

        val widthInPx = with(LocalDensity.current) { widthOfShadowBrush.toPx() }
        val transition = rememberInfiniteTransition(label = "Shimmer transition")
        val shimmerColors = listOf(
            Color.Transparent,
            Color.White.copy(alpha = shimmerMaxAlpha),
            Color.Transparent
        )
        val translateAnimation by transition.animateFloat(
            initialValue = 0f,
            targetValue = durationMillis + widthInPx,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = durationMillis,
                    easing = FastOutLinearInEasing,
                ),
                repeatMode = RepeatMode.Restart,
            ),
            label = "Shimmer loading animation",
        )
        val gradient = Brush.linearGradient(
            colors = shimmerColors,
            start = Offset(x = translateAnimation - widthInPx, y = 0.0f),
            end = Offset(x = translateAnimation, y = angleOfAxisY),
        )

        drawWithCache {
            onDrawWithContent {
                drawContent()
                drawRect(gradient)
            }
        }
    }
}


