package com.raya.challenge.common.result.error

sealed class ResultError {
    object None : ResultError()
    object Unknown : ResultError()
    object NetworkConnectionError : ResultError()
    object TimeOutError : ResultError()
    object ServerError : ResultError()
    data class ServiceError(
        val code: String,
        val title: String?,
        val message: String?
    ) : ResultError()
}