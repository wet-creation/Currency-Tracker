package com.mycompany.currencytracker.domain.model.currency.crypto

import com.mycompany.currencytracker.data.remote.dto.currency.crypto.CryptoDto
import com.mycompany.currencytracker.domain.model.currency.CurrencyListItem

data class CryptoGeneralInfo(
    val rate: Double,
    val image: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCap: Long,
    val _24h: Double?,
    val _7d: Double?,
    val _30d: Double?
): CurrencyListItem {
    constructor() : this(
        0.0,
        "",
        0,
        "",
        "",
        0,
        null,
        null,
        null,
    )
}


fun CryptoDto.toCryptoGeneralInfo() = CryptoGeneralInfo(
    current_price,
    image,
    market_cap_rank,
    name,
    symbol,
    market_cap,
    _24h,
    _7d,
    _30d
)