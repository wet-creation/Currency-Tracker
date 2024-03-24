package com.mycompany.currencytracker.domain.model.user

data class User (
    val id: String = "",
    val name: String,
    val surname: String,
    val email: String,
    val password: String
) {
    constructor() : this("", "", "", "", "")
}




