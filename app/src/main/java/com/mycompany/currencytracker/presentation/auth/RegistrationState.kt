package com.mycompany.currencytracker.presentation.auth

import com.mycompany.currencytracker.presentation.common.UiText

data class RegistrationState(
    val isLoading: Boolean = false,
    val isRegistry: Boolean = false,
    val error: UiText = UiText.DynamicString("")
)
