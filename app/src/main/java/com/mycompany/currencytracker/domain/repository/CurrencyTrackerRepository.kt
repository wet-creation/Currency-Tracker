package com.mycompany.currencytracker.domain.repository

import com.mycompany.currencytracker.data.remote.dto.currency.ConvertDto
import com.mycompany.currencytracker.data.remote.dto.currency.CurrencyNameDto
import com.mycompany.currencytracker.data.remote.dto.currency.RateDto

interface CurrencyTrackerRepository {

    suspend fun getLatest(): List<RateDto>
    suspend fun getLatestBySymbol(symbol: String): RateDto
    suspend fun getConvert(
        value: Double,
        from: String,
        to: String
    ): ConvertDto

    suspend fun getHistoricalByOneDate(
        date: String,
        symbol: String?
    ): List<RateDto>

    suspend fun getHistoricalByDateRange(
        startDate: String,
        endDate: String,
        symbol: String?
    ): List<RateDto>

    suspend fun getCurrenciesName(symbol: String? = null): List<CurrencyNameDto>
}