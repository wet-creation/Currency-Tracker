package com.mycompany.currencytracker.data.repository

import com.mycompany.currencytracker.data.remote.CurrencyTrackerApi
import com.mycompany.currencytracker.data.remote.dto.currency.ConvertDto
import com.mycompany.currencytracker.data.remote.dto.currency.CurrencyNameDto
import com.mycompany.currencytracker.data.remote.dto.currency.RateDto
import com.mycompany.currencytracker.domain.repository.CurrencyTrackerRepository
import javax.inject.Inject

class CurrencyTrackerImp @Inject constructor(
    private val api: CurrencyTrackerApi
): CurrencyTrackerRepository {
    override suspend fun getLatest(): List<RateDto> {
       return api.getLatest()
    }

    override suspend fun getLatestBySymbol(symbol: String): RateDto {
        return api.getLatestBySymbol(symbol)
    }

    override suspend fun getConvert(value: Double, from: String, to: String): ConvertDto {
        return api.getConvert(value,from, to)
    }

    override suspend fun getHistoricalByOneDate(date: String, symbol: String?): List<RateDto> {
        return api.getHistoricalByOneDate(date, symbol)
    }

    override suspend fun getHistoricalByDateRange(
        startDate: String,
        endDate: String,
        symbol: String?
    ): List<RateDto> {
        return api.getHistoricalByDateRange(startDate, endDate, symbol)
    }

    override suspend fun getCurrenciesName(symbol: String?): List<CurrencyNameDto> {
        return api.getCurrenciesName(symbol)
    }


}