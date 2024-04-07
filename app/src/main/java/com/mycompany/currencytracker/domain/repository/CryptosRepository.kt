package com.mycompany.currencytracker.domain.repository

import com.mycompany.currencytracker.data.remote.dto.currency.crypto.CryptoDto

interface CryptosRepository {
    suspend fun getLatest(baseCurrency: String = "USD"): List<CryptoDto>
    suspend fun getLatestBySymbol(symbol: String, baseCurrency: String = "USD"): CryptoDto
    suspend fun getPeriod(timestamp: Long, symbol: String, baseCurrency: String = "USD"): List<CryptoDto>
    suspend fun getHistoricalByOneDate(
        date: String,
        symbol: String? = null,
        baseCurrency: String = "USD"
    ): List<CryptoDto>

    suspend fun getHistoricalByDateRange(
        startDate: String,
        endDate: String,
        symbol: String? = null,
        baseCurrency: String = "USD"
    ): List<CryptoDto>
}