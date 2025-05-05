package com.raya.challenge.balance.impl.domain.repository

import com.raya.challenge.balance.impl.domain.model.BalanceTransactionModel
import com.raya.challenge.common.domain.model.Currency
import com.raya.challenge.common.result.ResponseResult

internal interface BalanceRepository {

    suspend fun getBalanceTransaction(): ResponseResult<BalanceTransactionModel>
    suspend fun executeSwap(
        currencyFrom: Currency,
        currencyTo: Currency,
        amountFrom: Double,
        amountTo: Double
    ): ResponseResult<Unit>
}