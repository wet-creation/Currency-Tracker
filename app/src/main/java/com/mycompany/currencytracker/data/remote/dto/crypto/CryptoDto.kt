package com.mycompany.currencytracker.data.remote.dto.crypto

import com.mycompany.currencytracker.domain.model.crypto.CryptoDetails

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
) {
    fun toCrypto() = CryptoDetails(
        current_price,
        id,
        image,
        last_updated,
        market_cap_rank,
        name,
        symbol,
        market_cap,
        max_supply,
        total_supply,
        ath,
        ath_timestamp,
        atl,
        atl_timestamp,
        circulating_supply
    )
}