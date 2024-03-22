package com.mycompany.currencytracker.presentation.currency_detail

import com.mycompany.currencytracker.domain.model.currency.fiat.FiatAdditionalInfo

data class CurrencyDetailState (
    val isLoading: Boolean = false,
    val currency: FiatAdditionalInfo? = null,
    val error: String = ""
)