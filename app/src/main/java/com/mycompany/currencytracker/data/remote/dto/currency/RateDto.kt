package com.mycompany.currencytracker.data.remote.dto.currency

data class RateDto(
    val symbol: String,
    val timestamp: Long,
    val rate: Double,
    val id: Long
)
