package com.mycompany.currencytracker.presentation.fiat_detail

import com.mycompany.currencytracker.domain.model.currency.fiat.FiatAdditionalInfo

data class FiatDetailState (
    val isLoading: Boolean = false,
    val currency: FiatAdditionalInfo? = null,
    val error: String = ""
)