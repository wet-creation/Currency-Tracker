package com.mycompany.currencytracker.data.remote.dto.user

data class UserLoginDto(
    val email: String,
    val password: String,
    val token: String
)
