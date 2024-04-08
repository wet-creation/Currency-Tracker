package com.mycompany.currencytracker.presentation.auth.register

data class UserInputRegistration(
    val emailInput: String = "",
    val nameInput: String = "",
    val surnameInput: String = "",
    val passwordInput: String = "",
    val repeatPasswordInput: String = "",
    val isPasswordVisible: Boolean = false
)
