package com.raya.challenge.common.result

import com.raya.challenge.common.result.error.ResultError

sealed class ResponseResult<out R> {
    data class Success<out T>(val data: T) : ResponseResult<T>()
    data class Error(val resultError: ResultError) : ResponseResult<Nothing>()
}

inline fun <A, B, R> ResponseResult<A>.zipWith(
    other: ResponseResult<B>,
    crossinline combine: (A, B) -> R
): ResponseResult<R> {
    return when {
        this is ResponseResult.Success && other is ResponseResult.Success -> {
            ResponseResult.Success(combine(this.data, other.data))
        }
        this is ResponseResult.Error -> this as ResponseResult<R>
        other is ResponseResult.Error -> other as ResponseResult<R>
        else -> ResponseResult.Error(ResultError.Unknown)
    }
}