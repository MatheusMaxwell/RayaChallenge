package com.raya.challenge.common.result.mapper

import com.raya.challenge.common.result.ResponseResult
import com.raya.challenge.common.result.viewstate.ResultViewState


fun <T, R> ResponseResult<T>.map(transform: (T) -> R): ResponseResult<R> {
    return when (this) {
        is ResponseResult.Success -> ResponseResult.Success(transform(this.data))
        is ResponseResult.Error -> ResponseResult.Error(this.resultError)
    }
}

fun <T, R> ResponseResult<T>.toResultViewState(transform: (T) -> R): ResultViewState<R> =
    when (this) {
        is ResponseResult.Success -> {
            ResultViewState.Success(transform(this.data))
        }
        is ResponseResult.Error -> {
            ResultViewState.Error(this.resultError)
        }
    }
