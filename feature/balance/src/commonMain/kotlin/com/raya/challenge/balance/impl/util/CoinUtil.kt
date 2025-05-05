package com.raya.challenge.balance.impl.util

import com.raya.challenge.balance.impl.domain.model.BalanceModel
import com.raya.challenge.common.domain.model.Currency

internal class CoinUtil(
    private val prices: Map<Currency, Map<Currency, Double>>,
    private val balance: List<BalanceModel>
) {

    fun getTotalAmountByCurrency(targetCurrency: Currency): Double {
        return balance.sumOf { item ->
            val fromCurrency = item.currency
            val amount = item.amount
            val rate = getConversionRate(fromCurrency, targetCurrency, prices)
            amount * rate
        }
    }

    fun convertAmountByCurrency(
        amount: Double,
        fromCurrency: Currency,
        toCurrency: Currency
    ): Double {
        val rate = getConversionRate(fromCurrency, toCurrency, prices)
        return amount * rate
    }

    private fun getConversionRate(
        from: Currency,
        to: Currency,
        prices: Map<Currency, Map<Currency, Double>>
    ): Double {
        if (from == to) return 1.0

        // Conversão direta
        prices[from]?.get(to)?.let { return it }

        // Conversão inversa
        prices[to]?.get(from)?.let { return 1.0 / it }

        // Tenta usar uma moeda intermediária como ponte (por ex. BTC ou ETH)
        val intermediaries = Currency.entries

        for (intermediary in intermediaries) {
            if (intermediary == from || intermediary == to) continue

            val fromToInter = prices[from]?.get(intermediary)
                ?: prices[intermediary]?.get(from)?.let { 1.0 / it }

            val interToTo = prices[intermediary]?.get(to)
                ?: prices[to]?.get(intermediary)?.let { 1.0 / it }

            if (fromToInter != null && interToTo != null) {
                return fromToInter * interToTo
            }
        }

        throw IllegalStateException("Não foi possível converter de $from para $to")
    }
}