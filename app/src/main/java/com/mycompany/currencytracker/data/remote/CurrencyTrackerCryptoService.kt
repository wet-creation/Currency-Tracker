package com.mycompany.currencytracker.data.remote

import com.mycompany.currencytracker.data.remote.dto.crypto.CryptoDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrencyTrackerCryptoService {
    @GET("latest/crypto")
    suspend fun getLatest(): List<CryptoDto>

    @GET("latest/{symbol}/crypto")
    suspend fun getLatestBySymbol(@Path("symbol") symbol: String): CryptoDto

    @GET("historical/{date}/crypto")
    suspend fun getHistoricalByOneDate(
        @Path("date") date: String,
        @Query("symbol") symbol: String? = null
    ): List<CryptoDto>

    @GET("historical/crypto")
    suspend fun getHistoricalByDateRange(
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
        @Query("symbol") symbol: String? = null
    ): List<CryptoDto>
}