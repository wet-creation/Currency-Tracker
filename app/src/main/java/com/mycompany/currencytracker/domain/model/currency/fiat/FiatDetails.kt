package com.mycompany.currencytracker.domain.model.currency.fiat

import com.mycompany.currencytracker.data.remote.dto.currency.fiat.CurrencyDto
import com.mycompany.currencytracker.domain.model.currency.IFiat

data class FiatDetails(
    override val symbol: String,
    val name: String,
    val timestamp: Long,
    override val rate: Double,
    val id: Long,
    override val _24h: Double?,
    override val _7d: Double?,
    override val _30d: Double?
) : IFiat {
    constructor() : this("", "", 0, 0.0, 0, null, null, null)
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



