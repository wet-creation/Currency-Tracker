package com.mycompany.currencytracker.domain.model.currency.fiat

data class Currency(
    val symbol: String,
    val name: String,
    val timestamp: Long,
    val rate: Double,
    val id: Long,
    val _24h: Double?,
    val _7d: Double?,
    val _30d: Double?
){
    constructor() : this("","",0,0.0,0, null, null, null)
}



