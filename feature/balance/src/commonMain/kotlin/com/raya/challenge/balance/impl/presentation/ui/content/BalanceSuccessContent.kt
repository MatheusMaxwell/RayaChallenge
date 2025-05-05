package com.raya.challenge.balance.impl.presentation.ui.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.raya.challenge.balance.impl.data.mapper.toDomain
import com.raya.challenge.balance.impl.domain.model.BalanceModel
import com.raya.challenge.balance.impl.domain.model.BalanceTransactionModel
import com.raya.challenge.balance.impl.util.CoinUtil
import com.raya.challenge.common.data.mock.BalanceTransactionDataMock
import com.raya.challenge.common.domain.model.Currency
import com.raya.challenge.common.ext.toFormattedCurrencyString
import com.raya.challenge.common.util.DateUtil
import com.raya.challenge.designsystem.component.button.SecondaryButton
import com.raya.challenge.designsystem.component.currencydropdown.CurrencyDropdown
import com.raya.challenge.designsystem.theme.color.RayaColor
import com.raya.challenge.designsystem.theme.dimens.fontsize.FontSize
import com.raya.challenge.designsystem.theme.dimens.padding.Padding
import com.raya.challenge.designsystem.theme.dimens.size.CustomSize
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
internal fun BalanceSuccessContent(
    balanceTransactionModel: BalanceTransactionModel,
    reloadDataToShowError: () -> Unit
) {
    val selectedCurrency = remember { mutableStateOf(Currency.USD) }
    val coinUtil = CoinUtil(
        prices = balanceTransactionModel.prices,
        balance = balanceTransactionModel.balance
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RayaColor.background)
            .padding(Padding.medium)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(Padding.medium)
    ) {
        Spacer(modifier = Modifier.height(Padding.x2Large))
        Card(
            modifier = Modifier.fillMaxWidth().height(180.dp),
            shape = RoundedCornerShape(CustomSize.cornerRadius),
            elevation = 0.dp,
            backgroundColor = RayaColor.primaryLight
        ) {
            Row(
                modifier = Modifier.padding(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                CurrencyDropdown(
                    selectedCurrency = selectedCurrency.value,
                    onCurrencySelected = { selectedCurrency.value = it }
                )
                Spacer(modifier = Modifier.width(Padding.medium))
                Text(
                    text = coinUtil
                        .getTotalAmountByCurrency(selectedCurrency.value)
                        .toFormattedCurrencyString(selectedCurrency.value, isOnlyValue = true),
                    fontSize = FontSize.large,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(Padding.large))
        balanceTransactionModel.balance.forEach {
            BalanceCard(
                balance = it,
                coinUtil = coinUtil,
                selectedCurrency = selectedCurrency.value
            )
        }
        Spacer(modifier = Modifier.height(Padding.large))
        ExpandableTransactionCard(balanceTransactionModel)
        Spacer(modifier = Modifier.weight(1f))
        SecondaryButton(
            "(Dev mode) Reload data to show error",
            onClick = reloadDataToShowError
        )
    }
}

@Composable
private fun ExpandableTransactionCard(balanceTransactionModel: BalanceTransactionModel) {
    var expanded = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded.value = !expanded.value },
        shape = RoundedCornerShape(CustomSize.cornerRadius),
        elevation = 0.dp,
        backgroundColor = RayaColor.primaryLight
    ) {
        Column(modifier = Modifier.padding(Padding.medium)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Transacciones",
                    fontSize = FontSize.small,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Icon(
                    imageVector = if (expanded.value) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color.White
                )
            }

            AnimatedVisibility(visible = expanded.value) {
                Column {
                    Spacer(modifier = Modifier.height(Padding.medium))
                    balanceTransactionModel.transactions.forEach {
                        Text(
                            text = "${it.amountFrom.toFormattedCurrencyString(it.from)} -> ${it.amountTo.toFormattedCurrencyString(it.to)}",
                            color = Color.White
                        )
                        Text(
                            text = DateUtil.getFormattedDateTime(it.date),
                            fontSize = FontSize.ultraSmall,
                            color = Color.White,
                            modifier = Modifier.padding(bottom = Padding.small)
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun BalanceCard(
    balance: BalanceModel,
    coinUtil: CoinUtil,
    selectedCurrency: Currency
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(CustomSize.cornerRadius),
        elevation = 0.dp,
        backgroundColor = RayaColor.primaryLight
    ) {
        Row(
            Modifier.padding(Padding.medium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = balance.currency.icon,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.size(CustomSize.coinImage).clip(CircleShape).background(MaterialTheme.colors.onSurface.copy(alpha = 0.1f))
                )
                Spacer(modifier = Modifier.width(Padding.small))
                Column {
                    Text(text = balance.currency.description, fontWeight = FontWeight.Bold, color = Color.White)
                    Text(text = balance.amount.toFormattedCurrencyString(balance.currency), color = Color.White)
                }
            }
            Text(
                text = coinUtil.convertAmountByCurrency(
                    amount = balance.amount,
                    fromCurrency = balance.currency,
                    toCurrency = selectedCurrency
                ).toFormattedCurrencyString(
                    selectedCurrency
                ),
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
private fun BalanceSuccessContentPreview() {
    BalanceSuccessContent(
        balanceTransactionModel = BalanceTransactionDataMock.balanceTransactionData.toDomain(),
        reloadDataToShowError = {}
    )
}