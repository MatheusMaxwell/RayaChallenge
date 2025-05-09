package com.raya.challenge.balance.impl.presentation.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.raya.challenge.designsystem.component.button.SecondaryButton
import com.raya.challenge.designsystem.theme.color.RayaColor
import com.raya.challenge.designsystem.theme.dimens.padding.Padding
import com.raya.challenge.designsystem.theme.dimens.size.CustomSize

@Composable
internal fun BalanceSwapResult(
    isSuccess: Boolean,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        if (isSuccess) {
            SuccessFullScreenModal {
                onDismiss()
            }
        } else {
            ErrorFullScreenModal {
                onDismiss()
            }
        }
    }
}

@Composable
private fun SuccessFullScreenModal(onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Success",
                modifier = Modifier.size(CustomSize.iconSize),
                tint = RayaColor.primary
            )

            Spacer(modifier = Modifier.height(Padding.medium))
            Text(
                text = "¡Transacción completada!",
                style = MaterialTheme.typography.h5.copy(
                    color = RayaColor.primary,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(Padding.small))
            Text(
                text = "Tu conversión se ha realizado con éxito.",
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(Padding.large))
            SecondaryButton(
                modifier = Modifier.fillMaxWidth(0.3f),
                text = "Aceptar",
                onClick = onDismiss
            )
        }
    }
}

@Composable
private fun ErrorFullScreenModal(onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = "Error",
                modifier = Modifier.size(CustomSize.iconSize),
                tint = Color.Red
            )

            Spacer(modifier = Modifier.height(Padding.medium))
            Text(
                text = "¡Error en la transacción!",
                style = MaterialTheme.typography.h5.copy(
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(Padding.small))
            Text(
                text = "Ocurrió un problema al procesar tu transacción.",
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(Padding.large))
            SecondaryButton(
                modifier = Modifier.fillMaxWidth(0.3f),
                text = "Volver",
                onClick = onDismiss
            )
        }
    }
}

@Preview
@Composable
private fun BalanceSwapResultSuccessPreview() {
    BalanceSwapResult(
        isSuccess = true,
        onDismiss = {}
    )
}

@Preview
@Composable
private fun BalanceSwapResultErrorPreview() {
    BalanceSwapResult(
        isSuccess = false,
        onDismiss = {}
    )
}