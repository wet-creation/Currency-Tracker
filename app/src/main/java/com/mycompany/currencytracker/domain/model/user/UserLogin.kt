package com.mycompany.currencytracker.domain.model.user

data class UserLogin(
    val email: String,
    val password: String,
    val token: String
)