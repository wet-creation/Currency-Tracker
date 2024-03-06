package com.mycompany.currencytracker.presentation.currency_list

import com.mycompany.currencytracker.domain.model.currency.fiat.Currency


data class CurrencyListState(
    val isLoading: Boolean = false,
    val currencies: List<Currency> = emptyList(),
    val error: String = ""

)
