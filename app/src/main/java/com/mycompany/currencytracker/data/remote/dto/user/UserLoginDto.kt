package com.mycompany.currencytracker.data.remote.dto.user

import com.mycompany.currencytracker.domain.model.user.UserLogin

data class UserLoginDto(
    val email: String,
    val password: String,
    val token: String
)

fun UserLogin.toUserLoginDto() = UserLoginDto(email, password, token)
