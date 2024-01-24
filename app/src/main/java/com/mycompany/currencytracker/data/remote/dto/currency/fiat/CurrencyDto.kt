package com.mycompany.currencytracker.data.remote.dto.currency.fiat

import com.mycompany.currencytracker.domain.model.currency.fiat.Currency

data class CurrencyDto(
    val id: Long,
    val symbol: String,
    val timestamp: Long,
    val rate: Double,
    val name: String,
    val _24h: Double?,
    val _7d: Double?,
    val _30d: Double?,
) {
    fun toCurrency(): Currency = Currency(
        symbol,
        name,
        timestamp,
        rate,
        id,
        _24h,
        _7d,
        _30d
    )
}
