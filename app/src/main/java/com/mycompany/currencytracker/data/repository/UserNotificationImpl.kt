package com.mycompany.currencytracker.data.repository

import com.mycompany.currencytracker.data.remote.dto.user.UserNotificationDto
import com.mycompany.currencytracker.data.remote.dto.user.UserNotificationSaveDto
import com.mycompany.currencytracker.data.remote.services.user.UserServiceNotification
import com.mycompany.currencytracker.domain.repository.UserNotificationRepository
import javax.inject.Inject

class UserNotificationImpl @Inject constructor(
    val api: UserServiceNotification
): UserNotificationRepository {
    override suspend fun getAll(email: String, baseCurrency: String): List<UserNotificationDto> {
        return api.getAll(email, baseCurrency)
    }

    override suspend fun get(id: String): UserNotificationDto {
        return api.get(id)
    }

    override suspend fun save(userNotificationDto: UserNotificationSaveDto) {
        api.save(userNotificationDto)
    }

    override suspend fun delete(id: String) {
        api.delete(id)
    }
}