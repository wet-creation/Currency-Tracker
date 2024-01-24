package com.mycompany.currencytracker.domain.model.currency.crypto

data class CryptoDetails(
    val rate: Double,
    val id: String,
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
    val _24h: Double?,
    val _7d: Double?,
    val _30d: Double?
) {
    constructor() : this(
        0.0,
        "",
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