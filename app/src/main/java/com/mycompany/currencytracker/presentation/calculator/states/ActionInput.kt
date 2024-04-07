package com.mycompany.currencytracker.presentation.calculator.states

sealed class ActionInput {
    data class Number(val number: Int): ActionInput()
    object Decimal: ActionInput()
    object Erase: ActionInput()
}