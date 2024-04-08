package com.mycompany.currencytracker.presentation.crypto_list

import com.mycompany.currencytracker.domain.model.currency.crypto.CryptoGeneralInfo
import com.mycompany.currencytracker.presentation.common.UiText
import com.mycompany.currencytracker.presentation.common.emptyUiText
import com.mycompany.currencytracker.presentation.common.list.IListState

data class CryptoListState(
    override val isLoading: Boolean = false,
    override val currencies: List<CryptoGeneralInfo> = emptyList(),
    override val error: UiText = emptyUiText

): IListState<CryptoGeneralInfo>