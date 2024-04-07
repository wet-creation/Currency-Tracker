package com.mycompany.currencytracker.domain.model.currency.crypto

import com.mycompany.currencytracker.data.remote.dto.currency.crypto.CryptoDto
import com.mycompany.currencytracker.domain.model.currency.IChangeRates

data class CryptoDetails(
    override val rate: Double,
    val image: String,
    val timestamp: Int,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCap: Long,
    val maxSupply: Double?,
    val totalSupply: Double?,
    val ath: Double,
    val athTimestamp: Long,
    val atl: Double,
    val atlTimestamp: Long,
    val circulatingSupply: Double,
    override val _24h: Double?,
    override val _7d: Double?,
    override val _30d: Double?
) : IChangeRates {
    constructor() : this(
        0.0,
        "",
        0,
        0,
        "",
        "",
        0,
        null,
        null,
        0.0,
        0,
        0.0,
        0,
        0.0,
        null,
        null,
        null
    )
}

fun CryptoDto.toCryptoDetails() = CryptoDetails(
    current_price,
    image,
    last_updated,
    market_cap_rank,
    name,
    symbol,
    market_cap,
    max_supply,
    total_supply,
    ath,
    ath_timestamp,
    atl,
    atl_timestamp,
    circulating_supply,
    _24h,
    _7d,
    _30d
)