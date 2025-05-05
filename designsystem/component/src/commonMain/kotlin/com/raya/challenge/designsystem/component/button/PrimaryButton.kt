package com.raya.challenge.designsystem.component.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.raya.challenge.designsystem.theme.color.RayaColor
import com.raya.challenge.designsystem.theme.dimens.size.CustomSize

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .height(CustomSize.buttonHeight)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = RayaColor.primary,
            disabledBackgroundColor = Color.LightGray,
            disabledContentColor = RayaColor.primary.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(CustomSize.cornerRadius),
    ) {
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.button.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}
