package com.mycompany.currencytracker.presentation.auth.login

data class UserInputLogin(
    val emailInput: String = "",
    val passwordInput: String = "",
    val isPasswordShown: Boolean = false
)
