package com.raya.challenge.balance.impl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raya.challenge.balance.impl.domain.model.BalanceTransactionModel
import com.raya.challenge.balance.impl.domain.usecase.ExecuteSwapParams
import com.raya.challenge.balance.impl.domain.usecase.ExecuteSwapUseCase
import com.raya.challenge.balance.impl.domain.usecase.GetBalanceTransactionUseCase
import com.raya.challenge.common.domain.model.Currency
import com.raya.challenge.common.result.error.ResultError
import com.raya.challenge.common.result.mapper.toResultViewState
import com.raya.challenge.common.result.viewstate.ResultViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class BalanceViewModel(
    private val getBalanceTransactionUseCase: GetBalanceTransactionUseCase,
    private val executeSwapUseCase: ExecuteSwapUseCase
): ViewModel() {

    private val _balanceUiState: MutableStateFlow<ResultViewState<BalanceTransactionModel>> = MutableStateFlow(ResultViewState.Loading)
    val balanceUiState: StateFlow<ResultViewState<BalanceTransactionModel>> = _balanceUiState
        .onStart {
            getBalanceTransactions()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ResultViewState.Loading
        )
    private val _dialogSwapState: MutableStateFlow<SwapDialogViewState> = MutableStateFlow(SwapDialogViewState.None)
    val dialogSwapState: StateFlow<SwapDialogViewState> = _dialogSwapState


    private fun getBalanceTransactions() {
        viewModelScope.launch {
            val state = getBalanceTransactionUseCase.invoke().toResultViewState { it }
            _balanceUiState.update { state }
        }
    }

    /*
    // This function is used to simulate an error when loading data
     */
    fun reloadDataWithError() {
        viewModelScope.launch {
            _balanceUiState.update { ResultViewState.Loading }
            delay(1000)
            _balanceUiState.update { ResultViewState.Error(ResultError.Unknown) }
        }
    }

    fun updateBalanceTransactions() {
        viewModelScope.launch {
            _balanceUiState.update { ResultViewState.Loading }
            val state = getBalanceTransactionUseCase.invoke().toResultViewState { it }
            _balanceUiState.update { state }
        }
    }

    fun executeSwap(
        currencyFrom: Currency,
        currencyTo: Currency,
        amountFrom: Double,
        amountTo: Double,
        executeWithError: Boolean
    ) {
        viewModelScope.launch {
            if (executeWithError) {
                delay(1000)
                _dialogSwapState.update { SwapDialogViewState.Error }
            } else {
                val state = executeSwapUseCase.invoke(
                    params = ExecuteSwapParams(
                        currencyFrom = currencyFrom,
                        currencyTo = currencyTo,
                        amountFrom = amountFrom,
                        amountTo = amountTo
                    )
                ).toSwapDialogViewState { it }
                _dialogSwapState.update { state }
                getBalanceTransactions()
            }
        }
    }

    fun dismissDialog() {
        _dialogSwapState.update { SwapDialogViewState.None }
    }
}
