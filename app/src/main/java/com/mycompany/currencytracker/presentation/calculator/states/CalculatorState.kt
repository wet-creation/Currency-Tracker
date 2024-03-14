package com.mycompany.currencytracker.presentation.calculator.states

data class CalculatorScreenState(
    val isLoading: Boolean = false,
    val sum1: Double = 0.0,
    val sum2: Double = 0.0,
    val currencySymbol1: String = "btc",
    val currencySymbol2: String = "USD",
    val error: String = ""
)

