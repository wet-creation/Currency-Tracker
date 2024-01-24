package com.mycompany.currencytracker.data.remote.dto.user

data class CurrencyFollowedList(
    val numberInList: Int,
    val userId: String,
    val fiatSymbol: String
)
