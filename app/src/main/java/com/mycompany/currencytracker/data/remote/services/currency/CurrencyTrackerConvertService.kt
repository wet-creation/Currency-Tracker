package com.mycompany.currencytracker.data.remote.services.currency

import com.mycompany.currencytracker.data.remote.dto.currency.ConvertDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyTrackerConvertService {
    @GET("convert")
    suspend fun getConvert(
        @Query("value") value: Double,
        @Query("from") from: String,
        @Query("to") to: String
    ): ConvertDto
}