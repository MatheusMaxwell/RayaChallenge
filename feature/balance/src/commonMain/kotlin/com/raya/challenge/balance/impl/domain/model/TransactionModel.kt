package com.raya.challenge.balance.impl.domain.model

import com.raya.challenge.common.domain.model.Currency

internal data class TransactionModel(
    val id: String,
    val from: Currency,
    val to: Currency,
    val amountFrom: Double,
    val amountTo: Double,
    val date: String
)
