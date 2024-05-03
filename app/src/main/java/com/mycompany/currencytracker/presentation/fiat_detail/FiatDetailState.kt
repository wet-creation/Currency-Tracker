package com.mycompany.currencytracker.presentation.fiat_detail

import com.mycompany.currencytracker.domain.model.currency.fiat.FiatAdditionalInfo
import com.mycompany.currencytracker.presentation.common.UiText
import com.mycompany.currencytracker.presentation.common.emptyUiText

data class FiatDetailState (
    val isLoading: Boolean = false,
    val currency: FiatAdditionalInfo? = null,
    val error: UiText = emptyUiText
)