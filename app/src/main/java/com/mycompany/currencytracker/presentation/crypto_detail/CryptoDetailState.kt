package com.mycompany.currencytracker.presentation.crypto_detail

import com.mycompany.currencytracker.domain.model.currency.crypto.CryptoDetails

data class CryptoDetailState (
    val isLoading: Boolean = false,
    val crypto: CryptoDetails? = null,
    val error: String = ""
)