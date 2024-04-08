package com.mycompany.currencytracker.presentation.auth.login

import com.mycompany.currencytracker.domain.model.user.User
import com.mycompany.currencytracker.presentation.common.UiText
import com.mycompany.currencytracker.presentation.common.emptyUiText

data class LoginState(
    val isLoading: Boolean = false,
    val result: User = User(),
    val error: UiText = emptyUiText,
    val httpError: UiText = emptyUiText,

)