package com.mycompany.currencytracker.data.repository

import com.mycompany.currencytracker.data.remote.CurrencyTrackerApiCurrencies
import com.mycompany.currencytracker.data.remote.dto.currency.ConvertDto
import com.mycompany.currencytracker.data.remote.dto.currency.CurrencyDto
import com.mycompany.currencytracker.domain.repository.CurrenciesRepository
import javax.inject.Inject

class CurrenciesImp @Inject constructor(
    private val api: CurrencyTrackerApiCurrencies
): CurrenciesRepository {
    override suspend fun getLatest(): List<CurrencyDto> {
       return api.getLatest()
    }

    override suspend fun getLatestBySymbol(symbol: String): CurrencyDto {
        return api.getLatestBySymbol(symbol)
    }

    override suspend fun getConvert(value: Double, from: String, to: String): ConvertDto {
        return api.getConvert(value,from, to)
    }

    override suspend fun getHistoricalByOneDate(date: String, symbol: String?): List<CurrencyDto> {
        return api.getHistoricalByOneDate(date, symbol)
    }

    override suspend fun getHistoricalByDateRange(
        startDate: String,
        endDate: String,
        symbol: String?
    ): List<CurrencyDto> {
        return api.getHistoricalByDateRange(startDate, endDate, symbol)
    }



}