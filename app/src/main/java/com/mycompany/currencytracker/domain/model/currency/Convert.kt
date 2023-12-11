package com.mycompany.currencytracker.domain.model.currency

data class Convert (
    val timestamp: Long,
    val rate: Double,
    val response: Double
)