package com.mycompany.currencytracker.presentation.currency_list

import com.mycompany.currencytracker.domain.model.currency.fiat.FiatDetails


data class CurrencyListState(
    val isLoading: Boolean = false,
    val currencies: List<FiatDetails> = emptyList(),
    val error: String = ""

)
