package com.mycompany.currencytracker.data.remote.services.currency

import com.mycompany.currencytracker.data.remote.dto.currency.fiat.CurrencyDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrencyTrackerCurrencyService {
    @GET("latest")
    suspend fun getLatest(@Query("baseCurrency") baseCurrency: String): List<CurrencyDto>

    @GET("period")
    suspend fun getPeriod(
        @Query("timestamp") timestamp: Long,
        @Query("symbol") symbol: String,
        @Query("baseCurrency") baseCurrency: String
    ): List<CurrencyDto>

    @GET("latest/{symbol}")
    suspend fun getLatestBySymbol(
        @Path("symbol") symbol: String,
        @Query("baseCurrency") baseCurrency: String
    ): CurrencyDto

    @GET("historical/{date}")
    suspend fun getHistoricalByOneDate(
        @Path("date") date: String,
        @Query("symbol") symbol: String?,
        @Query("baseCurrency") baseCurrency: String
    ): List<CurrencyDto>

    @GET("historical")
    suspend fun getHistoricalByDateRange(
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
        @Query("symbol") symbol: String?,
        @Query("baseCurrency") baseCurrency: String
    ): List<CurrencyDto>
}