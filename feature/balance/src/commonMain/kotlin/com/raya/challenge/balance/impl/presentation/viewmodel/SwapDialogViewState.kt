package com.raya.challenge.balance.impl.presentation.viewmodel

import com.raya.challenge.common.result.ResponseResult
import com.raya.challenge.common.result.viewstate.ResultViewState

sealed class SwapDialogViewState {
    data object None : SwapDialogViewState()
    data object Success : SwapDialogViewState()
    data object Error : SwapDialogViewState()
}

fun <T, R> ResponseResult<T>.toSwapDialogViewState(transform: (T) -> R): SwapDialogViewState =
    when (this) {
        is ResponseResult.Success -> {
            SwapDialogViewState.Success
        }
        is ResponseResult.Error -> {
            SwapDialogViewState.Error
        }
    }