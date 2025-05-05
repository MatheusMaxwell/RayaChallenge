package com.raya.challenge.feature.splash.impl.presentation.ui.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.raya.challenge.designsystem.component.background.StyledBackground
import com.raya.challenge.designsystem.component.rayaicon.RayaIcon
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview

private const val NAVIGATE_TO_LOGIN_DELAY = 2000L

@Composable
internal fun SplashContent(navigateToLogin: () -> Unit) {

    LaunchedEffect(Unit) {
        delay(NAVIGATE_TO_LOGIN_DELAY)
        navigateToLogin.invoke()
    }

    StyledBackground {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RayaIcon()
        }
    }
}


@Preview
@Composable
private fun SplashContentPreview() {
    SplashContent(
        navigateToLogin = {}
    )
}