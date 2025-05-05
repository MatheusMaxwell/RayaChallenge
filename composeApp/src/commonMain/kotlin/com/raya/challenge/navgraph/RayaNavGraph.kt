package com.raya.challenge.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.raya.challenge.balance.navigation.BalanceRoute
import com.raya.challenge.balance.navigation.balanceGraph
import com.raya.challenge.feature.login.navigation.LoginRoute
import com.raya.challenge.feature.login.navigation.loginGraph
import com.raya.challenge.feature.splash.navigation.SplashRoute
import com.raya.challenge.feature.splash.navigation.splashGraph

/**
 * Main navigation graph for the Art Gallery Viewer app.
 * @param startDestination The starting destination of the NavHost.
 * @param navController The NavController to be used by the NavHost.
 */
@Composable
fun RayaNavGraph(
    startDestination: Any = SplashRoute,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        startDestination = startDestination,
        navController = navController,
    ) {
        splashGraph(
            navigateToLogin = {
                navController.navigate(LoginRoute)
            }
        )
        loginGraph(
            navigateToBalance = {
                navController.navigate(BalanceRoute)
            }
        )
        balanceGraph()
    }
}