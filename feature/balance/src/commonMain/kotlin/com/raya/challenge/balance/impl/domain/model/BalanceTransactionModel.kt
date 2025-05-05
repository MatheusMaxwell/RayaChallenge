package com.raya.challenge.balance.impl.domain.model

import com.raya.challenge.common.domain.model.Currency

internal data class BalanceTransactionModel(
    val balance: List<BalanceModel>,
    val transactions: List<TransactionModel>,
    val prices: Map<Currency, Map<Currency, Double>>
)
