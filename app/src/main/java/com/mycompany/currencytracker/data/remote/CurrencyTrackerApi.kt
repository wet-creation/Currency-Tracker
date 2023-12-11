package com.mycompany.currencytracker.data.remote

import com.mycompany.currencytracker.data.remote.dto.currency.ConvertDto
import com.mycompany.currencytracker.data.remote.dto.currency.CurrencyNameDto
import com.mycompany.currencytracker.data.remote.dto.currency.RateDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrencyTrackerApi {
    @GET("latest")
    suspend fun getLatest(): List<RateDto>

    @GET("latest/{symbol}")
    suspend fun getLatestBySymbol(@Path("symbol") symbol: String): RateDto

    @GET("convert")
    suspend fun getConvert(
        @Query("value") value: Double,
        @Query("from") from: String,
        @Query("to") to: String
    ): ConvertDto

    @GET("historical/{date}")
    suspend fun getHistoricalByOneDate(
        @Path("date") date: String,
        @Query("symbol") symbol: String? = null
    ): List<RateDto>

    @GET("historical")
    suspend fun getHistoricalByDateRange(
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
        @Query("symbol") symbol: String? = null
    ): List<RateDto>

    @GET("currencies")
    suspend fun getCurrenciesName(@Query("symbol") symbol: String? = null): List<CurrencyNameDto>
}