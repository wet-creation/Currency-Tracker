package com.mycompany.currencytracker.data.repository

import com.mycompany.currencytracker.data.remote.CurrencyTrackerCurrencyService
import com.mycompany.currencytracker.data.remote.dto.currency.CurrencyDto
import com.mycompany.currencytracker.domain.repository.CurrenciesRepository
import javax.inject.Inject

class CurrenciesImp @Inject constructor(
    private val api: CurrencyTrackerCurrencyService
): CurrenciesRepository {
    override suspend fun getLatest(baseCurrency: String): List<CurrencyDto> {
       return api.getLatest(baseCurrency)
    }

    override suspend fun getLatestBySymbol(symbol: String, baseCurrency: String): CurrencyDto {
        return api.getLatestBySymbol(symbol, baseCurrency)
    }

    override suspend fun getHistoricalByOneDate(
        date: String,
        symbol: String?,
        baseCurrency: String
    ): List<CurrencyDto> {
        return api.getHistoricalByOneDate(date, symbol, baseCurrency)
    }

    override suspend fun getHistoricalByDateRange(
        startDate: String,
        endDate: String,
        symbol: String?,
        baseCurrency: String
    ): List<CurrencyDto> {
        return api.getHistoricalByDateRange(startDate, endDate, symbol, baseCurrency)
    }



}