package com.raya.challenge.feature.login.impl.presentation.ui

import androidx.compose.runtime.Composable
import com.raya.challenge.feature.login.impl.presentation.ui.content.LoginContent

@Composable
internal fun LoginScreen(navigateToBalance: () -> Unit) {
    LoginContent(
        navigateToBalance = navigateToBalance
    )
}