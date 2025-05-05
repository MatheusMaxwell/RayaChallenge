package com.raya.challenge.balance.impl.data.datasource.remote

import com.raya.challenge.common.data.model.BalanceTransactionData
import com.raya.challenge.common.result.ResponseResult

internal interface BalanceDataSourceRemote {

    suspend fun getBalanceTransaction(): ResponseResult<BalanceTransactionData>
    suspend fun executeSwap(
        currencyFrom: String,
        currencyTo: String,
        amountFrom: Double,
        amountTo: Double
    ): ResponseResult<Unit>
}