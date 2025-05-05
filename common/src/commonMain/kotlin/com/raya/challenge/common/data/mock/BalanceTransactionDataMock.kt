package com.raya.challenge.common.data.mock

import com.raya.challenge.common.data.model.BalanceData
import com.raya.challenge.common.data.model.BalanceTransactionData
import com.raya.challenge.common.data.model.TransactionData

object BalanceTransactionDataMock {

    var balance = ArrayList<BalanceData>()

    var transaction = ArrayList<TransactionData>()

    lateinit var balanceTransactionData: BalanceTransactionData

    fun init() {
        balance.addAll(
            listOf(
                BalanceData("ARS", 250000.0),
                BalanceData("USD", 3200.0),
                BalanceData("BTC", 0.004215),
                BalanceData("ETH", 1.154521)
            )
        )

        transaction.addAll(
            listOf(
                TransactionData(
                    id = "tx001",
                    from = "USD",
                    to = "BTC",
                    amountFrom = 500.0,
                    amountTo = 0.0082,
                    date = "2025-04-30T14:12:00Z"
                ),
                TransactionData(
                    id = "tx002",
                    from = "ARS",
                    to = "USD",
                    amountFrom = 100000.0,
                    amountTo = 115.0,
                    date = "2025-04-29T10:05:00Z"
                )
            )
        )

        balanceTransactionData = BalanceTransactionData(
            balance = balance,
            transactions = transaction,
            prices = mapOf()
        )
    }
}