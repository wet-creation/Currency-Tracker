package com.mycompany.currencytracker.data.remote.dto.currency

import com.mycompany.currencytracker.domain.model.currency.Convert

data class ConvertDto(
    val timestamp: Long,
    val rate: Double,
    val response: Double
) {
    fun toConvert() = Convert(timestamp, rate, response)
}




