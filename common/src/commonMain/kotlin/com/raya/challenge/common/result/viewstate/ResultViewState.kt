package com.raya.challenge.common.result.viewstate

import com.raya.challenge.common.result.error.ResultError


sealed class ResultViewState<out R> {
    data object Loading : ResultViewState<Nothing>()
    data class Success<out T>(val data: T) : ResultViewState<T>()
    data class Error(val resultError: ResultError) : ResultViewState<Nothing>()
}
