package com.mycompany.currencytracker.domain.model.currency

interface IChangeRates {
    val rate: Double
    val _24h: Double?
    val _7d: Double?
    val _30d: Double?
}