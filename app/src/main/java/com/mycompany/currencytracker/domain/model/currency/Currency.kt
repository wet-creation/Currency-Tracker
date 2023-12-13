package com.mycompany.currencytracker.domain.model.currency

data class Currency(
    val symbol: String,
    val name: String,
    val timestamp: Long,
    val rate: Double,
    val id: Long
){
    constructor() : this("","",0,0.0,0)
}



