package com.mycompany.currencytracker.domain.model.currency

interface IFiat: IChangeRates {
    val symbol: String
}