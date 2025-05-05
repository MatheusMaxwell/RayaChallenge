package com.raya.challenge.designsystem.component.rayaicon

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun RayaIcon() {
    Column {
        Text(
            text = "R$",
            style = MaterialTheme.typography.h1.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = "Challenge",
            style = MaterialTheme.typography.h5.copy(
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        )
    }
}