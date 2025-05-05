package com.raya.challenge.common.ext

import com.raya.challenge.common.domain.model.Currency
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.roundToInt

fun Double.toFormattedCurrencyString(currency: Currency, isOnlyValue: Boolean = false): String {
    val decimals = when (currency) {
        Currency.ARS, Currency.USD -> 2
        Currency.BTC, Currency.ETH -> 6
    }
    val valueString = this.formatDecimalsSafe(decimals)
    return if (isOnlyValue) {
        valueString
    } else {
        "$valueString ${currency.name}"
    }
}

private fun Double.formatDecimalsSafe(decimals: Int): String {
    val factor = 10.0.pow(decimals)
    val rounded = (this * factor).roundToInt() / factor
    return rounded.toString().let {
        if (!it.contains(".")) it + "." + "0".repeat(decimals)
        else {
            val split = it.split(".")
            val decimalsPart = split[1].padEnd(decimals, '0')
            "${split[0]}.$decimalsPart"
        }
    }
}