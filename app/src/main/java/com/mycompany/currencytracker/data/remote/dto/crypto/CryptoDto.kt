package com.mycompany.currencytracker.data.remote.dto.crypto

import com.mycompany.currencytracker.domain.model.crypto.Crypto

data class CryptoDto(
    val current_price: Double,
    val id: String,
    val image: String,
    val last_updated: Int,
    val market_cap_rank: Int,
    val name: String,
    val symbol: String
) {
    fun toCrypto() = Crypto(current_price, id, image, last_updated, market_cap_rank, name, symbol)
}