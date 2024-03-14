package com.mycompany.currencytracker.domain.model.currency.fiat

import com.mycompany.currencytracker.data.remote.dto.currency.fiat.CurrencyDto
import com.mycompany.currencytracker.domain.model.currency.CurrencyListItem

data class FiatDetails(
    val symbol: String,
    val name: String,
    val timestamp: Long,
    val rate: Double,
    val id: Long,
    val _24h: Double?,
    val _7d: Double?,
    val _30d: Double?
): CurrencyListItem{
    constructor() : this("","",0,0.0,0, null, null, null)
}

fun CurrencyDto.toCurrency(): FiatDetails = FiatDetails(
    symbol,
    name,
    timestamp,
    rate,
    id,
    _24h,
    _7d,
    _30d
)



