package com.raya.challenge.balance.impl.data.datasource.remote

import com.raya.challenge.common.data.mock.BalanceTransactionDataMock
import com.raya.challenge.common.data.model.BalanceTransactionData
import com.raya.challenge.common.data.model.TransactionData
import com.raya.challenge.common.result.ResponseResult
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

private const val MOCK_DELAY = 1000L

internal class BalanceDataSourceRemoteImpl(): BalanceDataSourceRemote {

    init {
        BalanceTransactionDataMock.init()
    }

    override suspend fun getBalanceTransaction(): ResponseResult<BalanceTransactionData>
    = withContext(Dispatchers.Default) {
        delay(MOCK_DELAY)
        ResponseResult.Success(BalanceTransactionDataMock.balanceTransactionData)
    }

    @OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
    override suspend fun executeSwap(
        currencyFrom: String,
        currencyTo: String,
        amountFrom: Double,
        amountTo: Double
    ) = withContext(Dispatchers.Default) {
        delay(MOCK_DELAY)
        val updatedBalances = BalanceTransactionDataMock.balance.map { balance ->
            when(balance.currency) {
                currencyFrom -> balance.copy(amount = balance.amount - amountFrom)
                currencyTo -> balance.copy(amount = balance.amount + amountTo)
                else -> balance
            }
        }
        BalanceTransactionDataMock.balance.clear()
        BalanceTransactionDataMock.balance.addAll(updatedBalances)
        BalanceTransactionDataMock.transaction.add(
            TransactionData(
                id = Uuid.random().toString(),
                from = currencyFrom,
                to = currencyTo,
                amountFrom = amountFrom,
                amountTo = amountTo,
                date = Clock.System.now().toString()
            )
        )
        ResponseResult.Success(Unit)
    }
}