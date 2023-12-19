package com.mycompany.currencytracker.data.repository

import com.mycompany.currencytracker.data.remote.CurrencyTrackerCryptoService
import com.mycompany.currencytracker.data.remote.dto.crypto.CryptoDto
import com.mycompany.currencytracker.domain.repository.CryptosRepository
import javax.inject.Inject

class CryptosIml @Inject constructor(
    private val api: CurrencyTrackerCryptoService
) : CryptosRepository {
    override suspend fun getLatest(baseCurrency: String): List<CryptoDto> {
        return api.getLatest(baseCurrency)
    }

    override suspend fun getLatestBySymbol(symbol: String, baseCurrency: String): CryptoDto {
        return api.getLatestBySymbol(symbol, baseCurrency)
    }

    override suspend fun getHistoricalByOneDate(
        date: String,
        symbol: String?,
        baseCurrency: String
    ): List<CryptoDto> {
        return api.getHistoricalByOneDate(date, symbol, baseCurrency)
    }

    override suspend fun getHistoricalByDateRange(
        startDate: String,
        endDate: String,
        symbol: String?,
        baseCurrency: String
    ): List<CryptoDto> {
        return api.getHistoricalByDateRange(startDate, endDate, symbol, baseCurrency)
    }

}