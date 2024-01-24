package com.mycompany.currencytracker.data.remote.dto.user

data class CryptoFollowedDto(
    val userId: String,
    val numberInList: Int,
    val symbol: String,
    val market_cap: Long?,
    val image: String?,
    val _24h: Double?,
    val _7d: Double?,
    val _1m: Double?,
    val currentPrice: Double
)
