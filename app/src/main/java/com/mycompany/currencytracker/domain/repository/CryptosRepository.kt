package com.mycompany.currencytracker.domain.repository

import com.mycompany.currencytracker.data.remote.dto.crypto.CryptoDto

interface CryptosRepository {
    suspend fun getLatest(): List<CryptoDto>
    suspend fun getLatestBySymbol(symbol: String): CryptoDto

    suspend fun getHistoricalByOneDate(
        date: String,
        symbol: String?
    ): List<CryptoDto>

    suspend fun getHistoricalByDateRange(
        startDate: String,
        endDate: String,
        symbol: String?
    ): List<CryptoDto>
}