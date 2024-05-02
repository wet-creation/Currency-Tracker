package com.mycompany.currencytracker.domain.model.user.notification

import com.mycompany.currencytracker.data.remote.dto.user.UserNotificationSaveDto

data class UserNotification(
    val id: String,
    val userId: String,
    val symbol: String,
    val target: Double,
    val isMoreThanTarget: Boolean,
    val isConstantly: Boolean,
    val image: String?,
    val marketCapRank: Int,
    val name: String
) {
    fun toUserNotificationSaveDto() =
        UserNotificationSaveDto(userId, symbol, target, isMoreThanTarget, isConstantly)
}
