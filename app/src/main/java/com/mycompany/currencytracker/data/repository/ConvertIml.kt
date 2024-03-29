package com.mycompany.currencytracker.data.repository

import com.mycompany.currencytracker.data.remote.services.currency.CurrencyTrackerConvertService
import com.mycompany.currencytracker.data.remote.dto.currency.ConvertDto
import com.mycompany.currencytracker.domain.repository.ConvertRepository
import javax.inject.Inject

class ConvertIml @Inject constructor(
    private val api: CurrencyTrackerConvertService
): ConvertRepository {
    override suspend fun convert(value: Double, from: String, to: String): ConvertDto {
        return api.getConvert(value, from, to)
    }
}