package com.mycompany.currencytracker.data.remote.dto.user

data class UserNotificationDto(
    val id: String,
    val userId: String,
    val symbol: String,
    val target: Double,
    val isMoreThanTarget: Boolean,
    val isConstantly: Boolean,
)
