package com.raya.challenge.balance.impl.presentation.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raya.challenge.balance.impl.domain.model.BalanceModel
import com.raya.challenge.balance.impl.domain.model.BalanceTransactionModel
import com.raya.challenge.balance.impl.util.CoinUtil
import com.raya.challenge.common.domain.model.Currency
import com.raya.challenge.common.ext.toFormattedCurrencyString
import com.raya.challenge.designsystem.component.button.PrimaryButton
import com.raya.challenge.designsystem.component.button.SecondaryButton
import com.raya.challenge.designsystem.component.currencydropdown.CurrencyDropdown
import com.raya.challenge.designsystem.component.loading.LoadingDialogManager
import com.raya.challenge.designsystem.theme.color.RayaColor
import com.raya.challenge.designsystem.theme.dimens.fontsize.FontSize
import com.raya.challenge.designsystem.theme.dimens.padding.Padding
import com.raya.challenge.designsystem.theme.dimens.size.CustomSize
import kotlinx.coroutines.launch
import kotlin.requireNotNull

@Composable
internal fun BalanceSwapContent(
    balanceTransactionModel: BalanceTransactionModel,
    executeSwap: (currencyFrom: Currency, currencyTo: Currency, amountFrom: Double, amountTo: Double, executeWithError: Boolean) -> Unit
) {
    var convertFromCurrency by remember { mutableStateOf(Currency.USD) }
    var convertToCurrency by remember { mutableStateOf(Currency.BTC) }
    val coinUtil = CoinUtil(
        prices = balanceTransactionModel.prices,
        balance = balanceTransactionModel.balance
    )
    var transactionText by remember { mutableStateOf("") }
    var enableButton by remember { mutableStateOf(false) }

    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    var executeWithError by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Padding.large),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.SwapHoriz,
                    contentDescription = "Confirmar",
                    tint = RayaColor.primary,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(bottom = Padding.small)
                )
                Text(
                    "Confirmar transacción?",
                    style = MaterialTheme.typography.h6,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(Padding.medium))
                Text("De: $transactionText ${convertFromCurrency.name}", color = Color.DarkGray)
                Text("A: ${convertToCurrency.name}", color = Color.DarkGray)
                Text(
                    "Total a recibir: ${
                        coinUtil.convertAmountByCurrency(
                            amount = transactionText.toDoubleOrNull() ?: 0.0,
                            fromCurrency = convertFromCurrency,
                            toCurrency = convertToCurrency
                        )
                    }",
                    fontWeight = FontWeight.Bold,
                    color = RayaColor.primary
                )
                Spacer(modifier = Modifier.height(Padding.large))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("(Dev mode) Execute with error")
                    Checkbox(
                        checked = executeWithError,
                        onCheckedChange = { executeWithError = it }
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(Padding.medium)) {
                    PrimaryButton(
                        modifier = Modifier.weight(1f),
                        text = "Cancelar",
                        onClick = {
                            coroutineScope.launch { bottomSheetState.hide() }
                        }
                    )
                    SecondaryButton(
                        modifier = Modifier.weight(1f),
                        text = "Confirmar",
                        onClick = {
                            executeSwap.invoke(
                                convertFromCurrency,
                                convertToCurrency,
                                transactionText.toDoubleOrNull() ?: 0.0,
                                coinUtil.convertAmountByCurrency(
                                    amount = transactionText.toDoubleOrNull() ?: 0.0,
                                    fromCurrency = convertFromCurrency,
                                    toCurrency = convertToCurrency,
                                ),
                                executeWithError
                            )
                            transactionText = ""
                            coroutineScope.launch {
                                bottomSheetState.hide()
                            }
                        }
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(RayaColor.background)
                .padding(Padding.medium)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        keyboardController?.hide()
                    })
                },
            verticalArrangement = Arrangement.spacedBy(Padding.large),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.05f))
            CardConvertFrom(
                balance = requireNotNull(balanceTransactionModel.balance.find { it.currency == convertFromCurrency }),
                convertFromCurrency = convertFromCurrency,
                onConvertFromCurrencyChange = { convertFromCurrency = it },
                transactionValue = transactionText.toString(),
                onValueChange = { text, containsError ->
                    transactionText = text.orEmpty()
                    enableButton = containsError.not()
                }
            )
            ButtonChangeCurrencies {
                val temp = convertFromCurrency
                convertFromCurrency = convertToCurrency
                convertToCurrency = temp
                transactionText = ""
            }
            CardConvertTo(
                balance = requireNotNull(balanceTransactionModel.balance.find { it.currency == convertToCurrency }),
                totalValueConverted = coinUtil.convertAmountByCurrency(
                    amount = transactionText.toDoubleOrNull() ?: 0.0,
                    fromCurrency = convertFromCurrency,
                    toCurrency = convertToCurrency
                ),
                convertToCurrency = convertToCurrency,
                onConvertToCurrencyChange = { convertToCurrency = it }
            )

            SecondaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = "convertir",
                enabled = enableButton && convertFromCurrency != convertToCurrency,
                onClick = {
                    keyboardController?.hide()
                    coroutineScope.launch {
                        bottomSheetState.show()
                    }
                }
            )
        }
    }
}

@Composable
private fun ButtonChangeCurrencies(
    onClickChangeCurrencies: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable {
            onClickChangeCurrencies.invoke()
        },
        shape = CircleShape,
        color = RayaColor.primaryDark,
    ) {
        Icon(
            modifier = Modifier.padding(Padding.medium),
            imageVector = Icons.Default.SwapVert,
            contentDescription = "Change currencies",
            tint = Color.White
        )
    }
}

@Composable
private fun CardConvertFrom(
    balance: BalanceModel,
    convertFromCurrency: Currency,
    onConvertFromCurrencyChange: (Currency) -> Unit,
    transactionValue: String?,
    onValueChange: (String?, Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.2f),
        shape = RoundedCornerShape(CustomSize.cornerRadius),
        elevation = 0.dp,
        backgroundColor = RayaColor.primaryLight
    ) {
        Column(
            modifier = Modifier.padding(Padding.large),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DecimalInputField(
                    text = transactionValue,
                    maxAllowed = balance.amount,
                    onValueChange = onValueChange
                )
                CurrencyDropdown(
                    selectedCurrency = convertFromCurrency,
                    onCurrencySelected = onConvertFromCurrencyChange
                )
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = balance.amount.toFormattedCurrencyString(balance.currency),
                color = Color.LightGray,
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
private fun CardConvertTo(
    balance: BalanceModel,
    totalValueConverted: Double,
    convertToCurrency: Currency,
    onConvertToCurrencyChange: (Currency) -> Unit
) {
    val totalBalanceAfterTransaction = balance.amount + totalValueConverted
    Card(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.3f),
        shape = RoundedCornerShape(CustomSize.cornerRadius),
        elevation = 0.dp,
        backgroundColor = RayaColor.primaryLight
    ) {
        Column(
            modifier = Modifier.padding(Padding.large),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.padding(start = Padding.medium),
                text = "Recibe",
                color = Color.White
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = totalValueConverted.toFormattedCurrencyString(convertToCurrency, true),
                    style = TextStyle(fontSize = FontSize.large, color = Color.White, fontWeight = FontWeight.Bold)
                )
                CurrencyDropdown(
                    selectedCurrency = convertToCurrency,
                    onCurrencySelected = onConvertToCurrencyChange
                )
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = totalBalanceAfterTransaction.toFormattedCurrencyString(convertToCurrency),
                color = Color.LightGray,
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
private fun DecimalInputField(
    text: String?,
    maxAllowed: Double,
    onValueChange: (String?, Boolean) -> Unit
) {
    var isError by remember { mutableStateOf(false) }

    Column {
        Text(
            modifier = Modifier.padding(start = Padding.medium),
            text = "Pagar",
            color = Color.White
        )
        TextField(
            value = text.orEmpty(),
            onValueChange = { newText: String ->
                val sanitized = newText
                    .replace(',', '.')
                    .filterIndexed { index, c ->
                        c.isDigit() || (c == '.' && !newText.take(index).contains('.'))
                    }
                val parsed = sanitized.toDoubleOrNull()
                isError = parsed != null && parsed > maxAllowed
                onValueChange(sanitized, isError)
            },
            isError = isError,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Decimal
            ),
            placeholder = { Text(text = "0", style = TextStyle(fontSize = FontSize.large, color = Color.LightGray, fontWeight = FontWeight.Bold)) },
            textStyle = TextStyle(fontSize = FontSize.large, color = Color.White, fontWeight = FontWeight.Bold),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            )
        )

        if (isError) {
            Text(
                text = "Saldo insuficiente.",
                color = Color.Red
            )
        }
    }
}