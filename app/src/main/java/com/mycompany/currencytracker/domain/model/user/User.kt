package com.mycompany.currencytracker.domain.model.user

import com.mycompany.currencytracker.data.remote.dto.user.UserDto


data class User(
    val id: String = "",
    val name: String,
    val surname: String,
    val email: String,
    val password: String
) {
    constructor() : this("", "", "", "", "")

}

fun UserDto.toUser() = User(id, name, surname, email, password)




