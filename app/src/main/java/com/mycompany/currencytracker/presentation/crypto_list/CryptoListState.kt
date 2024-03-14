package com.mycompany.currencytracker.presentation.crypto_list

import com.mycompany.currencytracker.domain.model.currency.crypto.CryptoGeneralInfo

data class CryptoListState(
    val isLoading: Boolean = false,
    val cryptos: List<CryptoGeneralInfo> = emptyList(),
    val error: String = ""

)