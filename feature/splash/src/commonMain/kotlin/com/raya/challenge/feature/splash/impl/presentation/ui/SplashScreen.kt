package com.raya.challenge.feature.splash.impl.presentation.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.raya.challenge.feature.splash.impl.presentation.ui.content.SplashContent

@Composable
internal fun SplashScreen(
    navigateToLogin: () -> Unit
) {
    SplashContent(
        navigateToLogin = navigateToLogin
    )
}