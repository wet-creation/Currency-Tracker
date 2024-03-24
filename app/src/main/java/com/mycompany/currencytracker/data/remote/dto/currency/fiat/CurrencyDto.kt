package com.mycompany.currencytracker.data.remote.dto.currency.fiat

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
    constructor() : this(0,"",0,0.0,"", null, null, null)
}
