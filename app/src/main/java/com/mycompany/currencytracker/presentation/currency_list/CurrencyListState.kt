package com.mycompany.currencytracker.presentation.currency_list

import com.mycompany.currencytracker.domain.model.currency.fiat.FiatDetails
import com.mycompany.currencytracker.presentation.common.UiText
import com.mycompany.currencytracker.presentation.common.list.IListState
import com.mycompany.currencytracker.presentation.common.emptyUiText


data class CurrencyListState(
    override val isLoading: Boolean = false,
    override val currencies: List<FiatDetails> = emptyList(),
    override val error: UiText = emptyUiText

): IListState<FiatDetails>
