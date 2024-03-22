package com.mycompany.currencytracker.domain.model.currency.fiat

import com.mycompany.currencytracker.domain.model.currency.IChangeRates

data class FiatAdditionalInfo(
    val high24: Double,
    val low24: Double,
    val averageRate: Double,
    val open: Double,
    val close: Double,
    val symbol: String,
    val name: String,
    override val rate: Double,
    override val _24h: Double?,
    override val _7d: Double?,
    override val _30d: Double?
) : IChangeRates