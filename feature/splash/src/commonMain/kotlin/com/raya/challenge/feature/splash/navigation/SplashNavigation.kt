package com.raya.challenge.feature.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.raya.challenge.feature.splash.impl.presentation.ui.SplashScreen
import kotlinx.serialization.Serializable

@Serializable
data object SplashRoute

@Serializable
internal object SplashScreen

fun NavGraphBuilder.splashGraph(
    navigateToLogin: () -> Unit
) {
    navigation<SplashRoute>(
        startDestination = SplashScreen
    ) {
        composable<SplashScreen> { entry ->
            SplashScreen(
                navigateToLogin = navigateToLogin
            )
        }
    }
}
