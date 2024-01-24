package com.mycompany.currencytracker.data.remote.dto.user

data class UserDto(
    val id: String,
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val token: String,

)