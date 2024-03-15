package com.mycompany.currencytracker.presentation.common.currency.fiat

import androidx.compose.runtime.State
import com.mycompany.currencytracker.presentation.currency_list.CurrencyListState

interface IFiatViewModel {
    val state: State<CurrencyListState>
    fun getCurrencies()
}