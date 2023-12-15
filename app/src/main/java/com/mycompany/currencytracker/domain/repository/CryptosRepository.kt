package com.mycompany.currencytracker.domain.repository

import com.mycompany.currencytracker.data.remote.dto.crypto.CryptoDto

interface CryptosRepository {
    suspend fun getLatest(isBaseCrypto: Boolean = false): List<CryptoDto>
    suspend fun getLatestBySymbol(symbol: String, isBaseCrypto: Boolean = false): CryptoDto

    suspend fun getHistoricalByOneDate(
        date: String,
        symbol: String? = null,
        isBaseCrypto: Boolean = false
    ): List<CryptoDto>

    suspend fun getHistoricalByDateRange(
        startDate: String,
        endDate: String,
        symbol: String? = null,
        isBaseCrypto: Boolean = false
    ): List<CryptoDto>
}