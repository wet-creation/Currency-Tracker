package com.mycompany.currencytracker.domain.model.crypto

data class Crypto(
    val rate: Double,
    val id: String,
    val image: String,
    val timestamp: Int,
    val rank: Int,
    val name: String,
    val symbol: String
) {
    constructor() : this(0.0, "", "", 0,0,"","")
}