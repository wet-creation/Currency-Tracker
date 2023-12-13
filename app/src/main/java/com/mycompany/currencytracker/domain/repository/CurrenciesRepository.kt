package com.mycompany.currencytracker.domain.repository

import com.mycompany.currencytracker.data.remote.dto.currency.ConvertDto
import com.mycompany.currencytracker.data.remote.dto.currency.CurrencyDto

interface CurrenciesRepository {

    suspend fun getLatest(): List<CurrencyDto>
    suspend fun getLatestBySymbol(symbol: String): CurrencyDto
    suspend fun getConvert(
        value: Double,
        from: String,
        to: String
    ): ConvertDto

    suspend fun getHistoricalByOneDate(
        date: String,
        symbol: String?
    ): List<CurrencyDto>

    suspend fun getHistoricalByDateRange(
        startDate: String,
        endDate: String,
        symbol: String?
    ): List<CurrencyDto>
}