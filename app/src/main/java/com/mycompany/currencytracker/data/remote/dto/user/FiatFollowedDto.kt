package com.mycompany.currencytracker.data.remote.dto.user

data class FiatFollowedDto(
    val userId: String,
    val numberInList: Int,
    val symbol: String,
    val _24h: Double?,
    val _7d: Double?,
    val _1m: Double?,
    val rate: Double
)