package com.mycompany.currencytracker.presentation.crypto_list

import com.mycompany.currencytracker.domain.model.currency.crypto.CryptoGeneralInfo
import com.mycompany.currencytracker.presentation.common.UiText
import com.mycompany.currencytracker.presentation.common.emptyUiString

data class CryptoListState(
    val isLoading: Boolean = false,
    val cryptos: List<CryptoGeneralInfo> = emptyList(),
    val error: UiText = emptyUiString

)