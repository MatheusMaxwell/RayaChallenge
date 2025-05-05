package com.raya.challenge.balance.impl.data.repository

import com.raya.challenge.balance.impl.data.datasource.remote.BalanceDataSourceRemote
import com.raya.challenge.balance.impl.data.datasource.remote.PricesDataSourceRemote
import com.raya.challenge.balance.impl.data.mapper.toDomain
import com.raya.challenge.balance.impl.domain.model.BalanceTransactionModel
import com.raya.challenge.balance.impl.domain.repository.BalanceRepository
import com.raya.challenge.common.domain.model.Currency
import com.raya.challenge.common.result.ResponseResult
import com.raya.challenge.common.result.mapper.map
import com.raya.challenge.common.result.zipWith
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class BalanceRepositoryImpl(
    private val dataSourceRemote: BalanceDataSourceRemote,
    private val pricesDataSourceRemote: PricesDataSourceRemote,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
): BalanceRepository {
    override suspend fun getBalanceTransaction(): ResponseResult<BalanceTransactionModel> {
        return withContext(coroutineDispatcher) {
            val balanceResult = dataSourceRemote.getBalanceTransaction()
            val pricesResult = pricesDataSourceRemote.getPrices()
            balanceResult.zipWith(pricesResult) { balance, prices ->
                balance.copy(prices = prices).toDomain()
            }
        }
    }

    override suspend fun executeSwap(
        currencyFrom: Currency,
        currencyTo: Currency,
        amountFrom: Double,
        amountTo: Double
    ): ResponseResult<Unit> {
        return withContext(coroutineDispatcher) {
            dataSourceRemote.executeSwap(
                currencyFrom = currencyFrom.name,
                currencyTo = currencyTo.name,
                amountFrom = amountFrom,
                amountTo = amountTo
            )
        }
    }
}