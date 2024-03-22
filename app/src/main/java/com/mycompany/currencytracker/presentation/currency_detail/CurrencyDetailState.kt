package com.mycompany.currencytracker.presentation.currency_detail

import com.mycompany.currencytracker.domain.model.currency.fiat.FiatAdditionalInfo
import com.mycompany.currencytracker.domain.model.currency.fiat.FiatDetails

data class CurrencyDetailState (
    val isLoading: Boolean = false,
    val currency: FiatAdditionalInfo? = null,
    val error: String = ""
)