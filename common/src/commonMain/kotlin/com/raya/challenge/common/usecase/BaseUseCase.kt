package com.raya.challenge.common.usecase

import com.raya.challenge.common.result.ResponseResult

interface BaseUseCase<Param, Output> {
    suspend fun invoke(params: Param? = null): ResponseResult<Output>
}
