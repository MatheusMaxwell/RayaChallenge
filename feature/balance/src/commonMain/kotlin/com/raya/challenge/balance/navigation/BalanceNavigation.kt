package com.raya.challenge.balance.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.raya.challenge.balance.impl.presentation.ui.BalanceScreen
import kotlinx.serialization.Serializable

@Serializable
data object BalanceRoute

@Serializable
internal object BalanceScreen

fun NavGraphBuilder.balanceGraph() {
    navigation<BalanceRoute>(
        startDestination = BalanceScreen
    ) {
        composable<BalanceScreen> { entry ->
            BalanceScreen()
        }
    }
}