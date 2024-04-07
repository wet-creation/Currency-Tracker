package com.mycompany.currencytracker.presentation.common.currency

data class CurrencyListsScreenState (
    val inputText: String = "",
    val inputTextAction: (String) -> Unit = { }
)