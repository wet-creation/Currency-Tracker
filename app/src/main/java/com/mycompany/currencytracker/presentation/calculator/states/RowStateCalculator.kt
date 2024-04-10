package com.mycompany.currencytracker.presentation.calculator.states

data class RowStateCalculator(
    val image: Any = "",
    val symbol: String = "",
    val name: String = "",
    val sum: String = "0",
    val rate: Double = 1.0
)
