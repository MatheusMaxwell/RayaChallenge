package com.raya.challenge.common.result.mapper

import com.raya.challenge.common.result.error.ResultError
import com.raya.challenge.common.result.viewstate.ResultViewState

inline fun <T, R> ResultViewState<T>.map(transform: (T) -> R): ResultViewState<R> {
    return when (this) {
        is ResultViewState.Success -> ResultViewState.Success(transform(data))
        is ResultViewState.Loading -> ResultViewState.Loading
        is ResultViewState.Error -> ResultViewState.Error(resultError)
    }
}

inline fun <T> ResultViewState<T>.onSuccess(action: (value: T) -> Unit): ResultViewState<T> {
    if (this is ResultViewState.Success) action(data)
    return this
}

inline fun <T> ResultViewState<T>.onError(action: (error: ResultError) -> Unit): ResultViewState<T> {
    if (this is ResultViewState.Error) action(resultError)
    return this
}
