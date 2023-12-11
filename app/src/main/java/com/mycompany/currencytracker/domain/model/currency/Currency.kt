package com.mycompany.currencytracker.domain.model.currency

import com.mycompany.currencytracker.data.remote.dto.currency.CurrencyNameDto
import com.mycompany.currencytracker.data.remote.dto.currency.RateDto

data class Currency(
    val symbol: String,
    val name: String,
    val timestamp: Long,
    val rate: Double,
    val id: Long
)

fun toCurrancyList(
    currencyRate: List<RateDto>,
    currencyName: List<CurrencyNameDto>
): List<Currency> {
    val currencyList = mutableListOf<Currency>()
    for (rate in currencyRate) {
        val matchingCurrencyName = currencyName.find { it.symbol == rate.symbol }
        if (matchingCurrencyName != null) {
            currencyList.add(
                Currency(
                    symbol = rate.symbol,
                    name = matchingCurrencyName.name,
                    timestamp = rate.timestamp,
                    rate = rate.rate,
                    id = rate.id
                )
            )
        }
    }
    return currencyList
}


