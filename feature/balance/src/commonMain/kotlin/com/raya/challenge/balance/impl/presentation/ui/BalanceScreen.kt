package com.raya.challenge.balance.impl.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raya.challenge.balance.impl.presentation.ui.content.BalanceContent
import com.raya.challenge.balance.impl.presentation.viewmodel.BalanceViewModel
import com.raya.challenge.designsystem.component.loading.provider.LocalLoadingDialog
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun BalanceScreen() {
    val balanceViewModel = koinViewModel<BalanceViewModel>()
    val viewState by balanceViewModel.balanceUiState.collectAsStateWithLifecycle()
    val swapDialogState by balanceViewModel.dialogSwapState.collectAsStateWithLifecycle()
    val loadingDialog = LocalLoadingDialog.current

    BalanceContent(
        balanceUiState = viewState,
        swapDialogState = swapDialogState,
        loadingDialogManager = loadingDialog,
        executeSwap = { currencyFrom, currencyTo, amountFrom, amountTo, executeWithError ->
            balanceViewModel.executeSwap(
                currencyFrom = currencyFrom,
                currencyTo = currencyTo,
                amountFrom = amountFrom,
                amountTo = amountTo,
                executeWithError = executeWithError
            )
        },
        onDismissDialog = balanceViewModel::dismissDialog,
        onErrorRetry = balanceViewModel::updateBalanceTransactions,
        reloadDataToShowError = balanceViewModel::reloadDataWithError
    )
}