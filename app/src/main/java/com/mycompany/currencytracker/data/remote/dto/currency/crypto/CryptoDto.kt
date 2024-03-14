package com.mycompany.currencytracker.data.remote.dto.currency.crypto

import com.mycompany.currencytracker.domain.model.currency.crypto.CryptoDetails

data class CryptoDto(
    val current_price: Double,
    val id: String,
    val image: String,
    val last_updated: Int,
    val market_cap_rank: Int,
    val name: String,
    val symbol: String,
    val market_cap: Long,
    val max_supply: Double?,
    val total_supply: Double?,
    val ath: Double,
    val ath_timestamp: Long,
    val atl: Double,
    val atl_timestamp: Long,
    val circulating_supply: Double,
    val _24h: Double?,
    val _7d: Double?,
    val _30d: Double?,
) {

}