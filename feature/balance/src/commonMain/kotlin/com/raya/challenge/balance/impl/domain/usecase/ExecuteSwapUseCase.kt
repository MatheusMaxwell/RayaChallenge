package com.raya.challenge.balance.impl.domain.usecase

import com.raya.challenge.balance.impl.domain.repository.BalanceRepository
import com.raya.challenge.common.domain.model.Currency
import com.raya.challenge.common.result.ResponseResult
import com.raya.challenge.common.result.error.ResultError
import com.raya.challenge.common.usecase.BaseUseCase

internal class ExecuteSwapUseCase(
    private val balanceRepository: BalanceRepository
): BaseUseCase<ExecuteSwapParams, Unit> {
    override suspend fun invoke(params: ExecuteSwapParams?): ResponseResult<Unit> {
        return params?.let {
            balanceRepository.executeSwap(
                currencyFrom = it.currencyFrom,
                currencyTo = it.currencyTo,
                amountFrom = it.amountFrom,
                amountTo = it.amountTo
            )
        } ?: ResponseResult.Error(ResultError.Unknown)
    }
}

internal data class ExecuteSwapParams(
    val currencyFrom: Currency,
    val currencyTo: Currency,
    val amountFrom: Double,
    val amountTo: Double
)