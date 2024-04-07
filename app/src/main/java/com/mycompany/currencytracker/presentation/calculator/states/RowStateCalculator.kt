package com.mycompany.currencytracker.presentation.calculator.states

data class RowStateCalculator(
    val image: Any = "",
    val symbol: String = "",
    val name: String = "",
    val isOpen: Boolean = false,
    val sum: String = "0"
)
