package com.mycompany.currencytracker.presentation.auth

data class RegistrationState(
    val isLoading: Boolean = false,
    val isRegistry: Boolean = false,
    val error: String = ""
)
