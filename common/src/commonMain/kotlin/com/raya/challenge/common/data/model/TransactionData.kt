package com.raya.challenge.common.data.model

data class TransactionData(
    val id: String,
    val from: String,
    val to: String,
    val amountFrom: Double,
    val amountTo: Double,
    val date: String
)