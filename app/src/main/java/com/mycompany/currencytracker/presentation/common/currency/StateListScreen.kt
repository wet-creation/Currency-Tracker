package com.mycompany.currencytracker.presentation.common.currency

data class StateListScreen (
    val inputText: String = "1",
    val inputTextAction: (String) -> Unit = { }
)