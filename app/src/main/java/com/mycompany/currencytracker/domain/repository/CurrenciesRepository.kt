package com.mycompany.currencytracker.domain.repository

import com.mycompany.currencytracker.data.remote.dto.currency.fiat.CurrencyDto

interface CurrenciesRepository {

    suspend fun getLatest(baseCurrency: String = "USD"): List<CurrencyDto>
    suspend fun getLatestBySymbol(symbol: String, baseCurrency: String = "USD"): CurrencyDto

    suspend fun getHistoricalByOneDate(
        date: String,
        symbol: String? = null,
        baseCurrency: String = "USD"
    ): List<CurrencyDto>

    suspend fun getHistoricalByDateRange(
        startDate: String,
        endDate: String,
        symbol: String? = null,
        baseCurrency: String = "USD"
    ): List<CurrencyDto>
}