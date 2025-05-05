package com.raya.challenge.balance.impl.data.mapper

import com.raya.challenge.balance.impl.domain.model.BalanceModel
import com.raya.challenge.balance.impl.domain.model.BalanceTransactionModel
import com.raya.challenge.common.domain.model.Currency
import com.raya.challenge.balance.impl.domain.model.TransactionModel
import com.raya.challenge.common.data.model.BalanceData
import com.raya.challenge.common.data.model.BalanceTransactionData
import com.raya.challenge.common.data.model.TransactionData
import com.raya.challenge.common.ext.toCurrencyMap

internal fun BalanceTransactionData.toDomain() = BalanceTransactionModel(
    balance = balance.map { it.toDomain() },
    transactions = transactions.map { it.toDomain() },
    prices = prices.toCurrencyMap()
)

internal fun BalanceData.toDomain() = BalanceModel(
    currency = Currency.valueOf(currency),
    amount = amount
)

internal fun TransactionData.toDomain() = TransactionModel(
    id = id,
    from = Currency.valueOf(from),
    to = Currency.valueOf(to),
    amountFrom = amountFrom,
    amountTo = amountTo,
    date = date
)