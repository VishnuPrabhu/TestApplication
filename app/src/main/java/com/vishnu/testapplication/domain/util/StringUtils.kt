package com.vishnu.testapplication.domain.util

import com.vishnu.testapplication.ui.Constants.CURRENCY_CODE
import java.util.*

fun String?.toCurrencyAmount(currencyCode: String = CURRENCY_CODE): String {
    return "$currencyCode ${toAmount()}"
}

fun String?.toAmount(): String {
    val amount = if (this.isNullOrEmpty()) "0" else this
    return amount.toDoubleOrNull()?.formatAmount() ?: "0"
}

fun Double.formatAmount(): String {
    val currency = when(this) {
        0.0 -> "0"
        else -> String.format(Locale.US, "%,.2f", this)
    }
    return currency
}

fun String?.toNumber(): Double {
    val amount = if (this.isNullOrEmpty()) "0" else this.digits()
    return amount.toDoubleOrNull() ?: 0.0
}

fun Double.toNumberString(): String {
    return toBigDecimal().toPlainString()
}

private fun String.digits(): String {
    return this.replace(Regex("[^-\\d.]"), "")
}
