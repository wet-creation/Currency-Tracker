package com.mycompany.currencytracker.presentation.common.currency

data class StateListScreen (
    val inputText: String = "",
    val inputTextAction: (String) -> Unit = { }
)