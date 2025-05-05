package com.raya.challenge.common.data.model

data class BalanceTransactionData(
    val balance: List<BalanceData>,
    val transactions: List<TransactionData>,
    val prices: Map<String, Map<String, Double>>
)
