package com.mycompany.currencytracker.presentation.auth.register

import com.mycompany.currencytracker.presentation.common.UiText
import com.mycompany.currencytracker.presentation.common.emptyUiText

data class RegistrationState(
    val isLoading: Boolean = false,
    val isRegistry: Boolean = false,
    val emailNotValid: Boolean = false,
    val passwordNotValid: Boolean = false,
    val textInputNotValid: Boolean = false,
    val repeatPasswordNotValid: Boolean = false,
    val httpError: UiText = emptyUiText,
    val formError: UiText = emptyUiText
)
