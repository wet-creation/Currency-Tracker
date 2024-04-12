package com.mycompany.currencytracker.presentation.fiat_list

import com.mycompany.currencytracker.domain.model.currency.fiat.FiatDetails
import com.mycompany.currencytracker.presentation.common.UiText
import com.mycompany.currencytracker.presentation.common.emptyUiText
import com.mycompany.currencytracker.presentation.common.list.IListState


data class CurrencyListState(
    override val isLoading: Boolean = false,
    override val items: List<FiatDetails> = emptyList(),
    override val error: UiText = emptyUiText

): IListState<FiatDetails>
