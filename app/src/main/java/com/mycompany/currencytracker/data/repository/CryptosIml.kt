package com.mycompany.currencytracker.data.repository

import com.mycompany.currencytracker.data.remote.CurrencyTrackerCryptoService
import com.mycompany.currencytracker.data.remote.dto.crypto.CryptoDto
import com.mycompany.currencytracker.domain.repository.CryptosRepository
import javax.inject.Inject

class CryptosIml @Inject constructor(
    private val api: CurrencyTrackerCryptoService
) : CryptosRepository {
    override suspend fun getLatest(isBaseCrypto: Boolean): List<CryptoDto> {
        return api.getLatest(isBaseCrypto)
    }

    override suspend fun getLatestBySymbol(symbol: String, isBaseCrypto: Boolean): CryptoDto {
        return api.getLatestBySymbol(symbol, isBaseCrypto)
    }

    override suspend fun getHistoricalByOneDate(
        date: String,
        symbol: String?,
        isBaseCrypto: Boolean
    ): List<CryptoDto> {
        return api.getHistoricalByOneDate(date, symbol, isBaseCrypto)
    }

    override suspend fun getHistoricalByDateRange(
        startDate: String,
        endDate: String,
        symbol: String?,
        isBaseCrypto: Boolean
    ): List<CryptoDto> {
        return api.getHistoricalByDateRange(startDate, endDate, symbol, isBaseCrypto)
    }

}