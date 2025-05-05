package com.raya.challenge.common.ext

import com.raya.challenge.common.domain.model.Currency

fun Map<String, Map<String, Double>>.toCurrencyMap(): Map<Currency, Map<Currency, Double>> {
    val stringToCurrency = mapOf(
        "bitcoin" to Currency.BTC,
        "ethereum" to Currency.ETH,
        "usd" to Currency.USD,
        "ars" to Currency.ARS
    )

    return this.mapNotNull { (fromSymbol, toMap) ->
        val fromCurrency = stringToCurrency[fromSymbol.lowercase()] ?: return@mapNotNull null

        val convertedInner = toMap.mapNotNull { (toSymbol, value) ->
            val toCurrency = stringToCurrency[toSymbol.lowercase()] ?: return@mapNotNull null
            toCurrency to value
        }.toMap()

        fromCurrency to convertedInner
    }.toMap()
}