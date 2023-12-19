package com.mycompany.currencytracker.data.remote.dto.currency

import com.mycompany.currencytracker.domain.model.currency.Currency

data class CurrencyDto(
    val id: Long,
    val symbol: String,
    val timestamp: Long,
    val rate: Double,
    val name: String
) {
    fun toCurrency(): Currency = Currency(symbol, name, timestamp, rate, id)
}
