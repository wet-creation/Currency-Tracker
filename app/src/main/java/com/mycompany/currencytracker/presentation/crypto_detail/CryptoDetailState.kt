package com.mycompany.currencytracker.presentation.crypto_detail

import com.mycompany.currencytracker.domain.model.currency.crypto.CryptoDetails
import com.mycompany.currencytracker.presentation.common.UiText
import com.mycompany.currencytracker.presentation.common.emptyUiText

data class CryptoDetailState(
    val isLoading: Boolean = false,
    val cryptoSelected: CryptoDetails? = null,
    val error: UiText = emptyUiText
)