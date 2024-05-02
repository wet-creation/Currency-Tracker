package com.mycompany.currencytracker.data.remote.dto.user

import com.mycompany.currencytracker.domain.model.user.notification.UserNotification

data class UserNotificationDto(
    val id: String,
    val userId: String,
    val symbol: String,
    val target: Double,
    val isMoreThanTarget: Boolean,
    val isConstantly: Boolean,
) {
    fun toUserNotification() =
        UserNotification(id, userId, symbol, target, isMoreThanTarget, isConstantly, "", 0,"")
}
