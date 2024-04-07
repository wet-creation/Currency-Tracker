package com.mycompany.currencytracker.presentation.currency_list

import com.mycompany.currencytracker.domain.model.currency.fiat.FiatDetails
import com.mycompany.currencytracker.presentation.common.UiText
import com.mycompany.currencytracker.presentation.common.emptyUiText


data class CurrencyListState(
    val isLoading: Boolean = false,
    val currencies: List<FiatDetails> = emptyList(),
    val error: UiText = emptyUiText

)
