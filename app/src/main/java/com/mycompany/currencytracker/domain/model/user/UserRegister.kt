package com.mycompany.currencytracker.domain.model.user

data class UserRegister (
    val id: String = "",
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val passwordRepeat: String
) {
    constructor() : this("", "", "", "", "", "")
}




