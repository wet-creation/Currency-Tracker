package com.mycompany

import com.mycompany.currencytracker.data.remote.dto.currency.fiat.CurrencyDto

fun setCurrencyDto(
    id: Long = 0,
    symbol: String = "USD",
    timestamp: Long = 12121212123,
    rate: Double = 1.0,
    name: String = "Us dollar",
    _24h: Double? = 1.0,
    _7d: Double? = 1.0,
    _30d: Double? = 1.0,
): CurrencyDto = CurrencyDto(
    id, symbol, timestamp, rate, name, _24h, _7d, _30d
)