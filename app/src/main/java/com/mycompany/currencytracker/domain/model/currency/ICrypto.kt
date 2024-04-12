package com.mycompany.currencytracker.domain.model.currency

interface ICrypto: IChangeRates {
    val symbol: String
    val market_cap: Long?
    val image: String?
}