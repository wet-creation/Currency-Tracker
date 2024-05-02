package com.mycompany.currencytracker.domain.model.user.notification

import com.mycompany.currencytracker.domain.model.currency.crypto.CryptoGeneralInfo

data class CryptoSelectNotification(
    val symbol: String,
    val name: String,
    val image: String?
) {
    constructor() : this("","","")
}

fun CryptoGeneralInfo.toCryptoSelectNotification() = CryptoSelectNotification(symbol, name, image)
