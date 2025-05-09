package com.raya.challenge.designsystem.component.currencydropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.raya.challenge.common.domain.model.Currency
import com.raya.challenge.designsystem.theme.color.RayaColor
import com.raya.challenge.designsystem.theme.dimens.size.CustomSize

@Composable
fun CurrencyDropdown(
    selectedCurrency: Currency,
    onCurrencySelected: (Currency) -> Unit
) {
    val currencies = Currency.entries.map { it.name }
    var expanded = remember { mutableStateOf(false) }

    Box {
        Button(
            onClick = { expanded.value = true },
            shape = RoundedCornerShape(CustomSize.cornerRadius),
            colors = ButtonDefaults.buttonColors(backgroundColor = RayaColor.primaryDark)
        ) {
            Text(selectedCurrency.name, color = Color.White)
            Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.White)
        }

        DropdownMenu(
            modifier = Modifier.background(RayaColor.primaryDark),
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            currencies.forEach {
                DropdownMenuItem(
                    onClick = {
                        onCurrencySelected.invoke(Currency.valueOf(it))
                        expanded.value = false
                    }
                ) {
                    Text(it, color = Color.White)
                }
            }
        }
    }
}

@Preview
@Composable
private fun CurrencyDropdownPreview() {
    CurrencyDropdown(
        selectedCurrency = Currency.USD,
        onCurrencySelected = { _ -> }
    )
}