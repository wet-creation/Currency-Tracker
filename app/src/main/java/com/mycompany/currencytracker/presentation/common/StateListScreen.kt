package com.mycompany.currencytracker.presentation.common

data class StateListScreen (
    val inputText: String = "",
    val inputTextAction: (String) -> Unit = { }
)