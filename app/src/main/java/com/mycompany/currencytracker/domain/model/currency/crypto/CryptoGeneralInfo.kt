package com.mycompany.currencytracker.domain.model.currency.crypto

import com.mycompany.currencytracker.data.remote.dto.currency.crypto.CryptoDto
import com.mycompany.currencytracker.domain.model.currency.ICrypto

data class CryptoGeneralInfo(
    override val rate: Double,
    override val image: String,
    val rank: Int,
    val name: String,
    override val symbol: String,
    override val market_cap: Long,
    override val _24h: Double?,
    override val _7d: Double?,
    override val _30d: Double?,
    val id: String
): ICrypto {
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
        ""
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
    _1m,
    id
)