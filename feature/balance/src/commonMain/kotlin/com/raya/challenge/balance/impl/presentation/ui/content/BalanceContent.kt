package com.raya.challenge.balance.impl.presentation.ui.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.raya.challenge.balance.impl.data.mapper.toDomain
import com.raya.challenge.balance.impl.domain.model.BalanceTransactionModel
import com.raya.challenge.common.data.mock.BalanceTransactionDataMock
import com.raya.challenge.common.result.viewstate.ResultViewState
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.ui.graphics.Color
import com.raya.challenge.balance.impl.presentation.viewmodel.SwapDialogViewState
import com.raya.challenge.common.domain.model.Currency
import com.raya.challenge.designsystem.component.loading.LoadingDialogManager
import com.raya.challenge.designsystem.theme.color.RayaColor

@Composable
internal fun BalanceContent(
    balanceUiState: ResultViewState<BalanceTransactionModel>,
    swapDialogState: SwapDialogViewState,
    loadingDialogManager: LoadingDialogManager,
    executeSwap: (currencyFrom: Currency, currencyTo: Currency, amountFrom: Double, amountTo: Double, executeWithError: Boolean) -> Unit,
    onDismissDialog: () -> Unit,
    onErrorRetry: () -> Unit,
    reloadDataToShowError: () -> Unit
) {
    var selectedItem = remember { mutableStateOf(0) }

    Scaffold (
        bottomBar = {
            NavigationBar(
                containerColor = RayaColor.primary
            ) {
                NavigationBarItem(
                    selected = selectedItem.value == 0,
                    onClick = { selectedItem.value = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.White) },
                    label = { Text("Home", color = Color.White) },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = RayaColor.primaryDark
                    )
                )
                NavigationBarItem(
                    selected = selectedItem.value == 1,
                    onClick = { selectedItem.value = 1 },
                    icon = { Icon(Icons.Default.SwapHoriz, contentDescription = "Swap", tint = Color.White) },
                    label = { Text("Swap", color = Color.White) },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = RayaColor.primaryDark
                    )
                )
            }
        }
    ){ paddingValues ->
        when(swapDialogState) {
            SwapDialogViewState.None -> Unit
            is SwapDialogViewState.Error -> {
                loadingDialogManager.hide()
                BalanceSwapResult(isSuccess = false) {
                    onDismissDialog.invoke()
                }
            }
            is SwapDialogViewState.Success -> {
                loadingDialogManager.hide()
                BalanceSwapResult(isSuccess = true) {
                    onDismissDialog.invoke()
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = balanceUiState) {
                is ResultViewState.Error -> {
                    BalanceErrorContent(
                        onRetry = onErrorRetry
                    )
                }

                ResultViewState.Loading -> {
                    BalanceLoadingContent()
                }

                is ResultViewState.Success -> {
                    if (selectedItem.value == 0) {
                        BalanceSuccessContent(
                            balanceTransactionModel = state.data,
                            reloadDataToShowError = reloadDataToShowError
                        )
                    } else {
                        BalanceSwapContent(
                            balanceTransactionModel = state.data,
                            executeSwap = { currencyFrom, currencyTo, amountFrom, amountTo, executeWithError ->
                                loadingDialogManager.show()
                                executeSwap.invoke(
                                    currencyFrom,
                                    currencyTo,
                                    amountFrom,
                                    amountTo,
                                    executeWithError
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun BalanceContentPreview() {
    BalanceContent(
        balanceUiState = ResultViewState.Success(BalanceTransactionDataMock.balanceTransactionData.toDomain()),
        swapDialogState = SwapDialogViewState.None,
        loadingDialogManager = LoadingDialogManager(),
        executeSwap = { _, _, _, _, _ -> },
        onDismissDialog = {},
        onErrorRetry = {},
        reloadDataToShowError = {}
    )
}
