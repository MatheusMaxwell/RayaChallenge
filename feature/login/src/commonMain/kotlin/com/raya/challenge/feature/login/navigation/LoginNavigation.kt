package com.raya.challenge.feature.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.raya.challenge.feature.login.impl.presentation.ui.LoginScreen
import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute

@Serializable
internal object LoginScreen

fun NavGraphBuilder.loginGraph(
    navigateToBalance: () -> Unit
) {
    navigation<LoginRoute>(
        startDestination = LoginScreen
    ) {
        composable<LoginScreen> { entry ->
            LoginScreen(
                navigateToBalance = navigateToBalance
            )
        }
    }
}