package com.mycompany.currencytracker.domain.repository

import com.mycompany.currencytracker.data.remote.dto.ConvertDto

interface ConvertRepository {
    suspend fun convert(value: Double, from: String, to: String): ConvertDto
}