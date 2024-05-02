package com.mycompany.currencytracker.data.remote.dto.user

data class UserNotificationSaveDto(
    val userId: String,
    val symbol: String,
    val target: Double,
    val isMoreThanTarget: Boolean,
    val isConstantly: Boolean,
)
