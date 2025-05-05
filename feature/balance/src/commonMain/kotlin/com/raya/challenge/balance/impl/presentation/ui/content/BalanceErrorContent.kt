package com.raya.challenge.balance.impl.presentation.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raya.challenge.designsystem.component.button.SecondaryButton
import com.raya.challenge.designsystem.theme.color.RayaColor
import com.raya.challenge.designsystem.theme.dimens.fontsize.FontSize
import com.raya.challenge.designsystem.theme.dimens.padding.Padding
import com.raya.challenge.designsystem.theme.dimens.size.CustomSize
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun BalanceErrorContent(
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(RayaColor.primaryLight),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Padding.medium)
        ) {
            Icon(
                imageVector = Icons.Default.ErrorOutline,
                contentDescription = "Error",
                tint = Color.White,
                modifier = Modifier.size(CustomSize.iconSize)
            )
            Text(
                text = "¡Ups! Algo salió mal.",
                color = Color.White,
                fontSize = FontSize.small,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "No pudimos cargar tu información de saldo y transacciones.",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = FontSize.small
            )
            SecondaryButton(
                modifier = Modifier.fillMaxWidth(0.4f),
                text = "Reintentar",
                onClick = onRetry
            )
        }
    }
}

@Preview
@Composable
private fun BalanceErrorContentPreview() {
    BalanceErrorContent(
        onRetry = {}
    )
}