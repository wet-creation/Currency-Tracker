package com.mycompany.currencytracker.data.remote.dto.user

import com.mycompany.currencytracker.domain.model.user.User
import com.mycompany.currencytracker.domain.model.user.UserRegister

data class UserDto(
    val id: String,
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val token: String = "",

)

fun User.toUserDto() = UserDto(
    id, name, surname, email, password
)

fun UserRegister.toUserDto() = UserDto(
    id, name, surname, email, password
)
