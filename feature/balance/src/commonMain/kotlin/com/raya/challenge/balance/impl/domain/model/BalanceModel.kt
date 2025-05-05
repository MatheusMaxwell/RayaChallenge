package com.raya.challenge.balance.impl.domain.model

import com.raya.challenge.common.domain.model.Currency

internal data class BalanceModel(
    val currency: Currency,
    val amount: Double
)
