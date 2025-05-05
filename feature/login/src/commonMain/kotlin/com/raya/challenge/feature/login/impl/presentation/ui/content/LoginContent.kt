package com.raya.challenge.feature.login.impl.presentation.ui.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.raya.challenge.designsystem.component.background.StyledBackground
import com.raya.challenge.designsystem.component.button.PrimaryButton
import com.raya.challenge.designsystem.component.rayaicon.RayaIcon
import com.raya.challenge.designsystem.theme.dimens.padding.Padding
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun LoginContent(
    navigateToBalance: () -> Unit
) {
    StyledBackground {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RayaIcon()
            Spacer(modifier = Modifier.height(Padding.xLarge))
            PrimaryButton(
                modifier = Modifier.padding(Padding.large),
                text = "Login",
                onClick = {
                    navigateToBalance.invoke()
                }
            )
        }
    }
}

@Preview
@Composable
private fun LoginContentPreview() {
    LoginContent(
        navigateToBalance = {}
    )
}