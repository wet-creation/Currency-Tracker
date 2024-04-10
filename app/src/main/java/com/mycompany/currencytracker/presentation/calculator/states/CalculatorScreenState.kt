package com.mycompany.currencytracker.presentation.calculator.states

import com.mycompany.currencytracker.presentation.common.UiText
import com.mycompany.currencytracker.presentation.common.emptyUiText

data class CalculatorScreenState(
    val firstRowState: RowStateCalculator = RowStateCalculator(),
    val secondRowState: RowStateCalculator = RowStateCalculator(),
    val rate: Double = 1.0 ,
    val isLoading: Boolean = false,
    val error: UiText = emptyUiText
)
