package com.mycompany.currencytracker.data.repository

import com.mycompany.currencytracker.data.remote.dto.user.UserNotificationDto
import com.mycompany.currencytracker.data.remote.services.user.UserServiceNotification
import com.mycompany.currencytracker.domain.repository.UserNotificationRepository
import javax.inject.Inject

class UserNotificationImpl @Inject constructor(
    val api: UserServiceNotification
): UserNotificationRepository {
    override suspend fun getAll(email: String): List<UserNotificationDto> {
        return api.getAll(email)
    }

    override suspend fun get(id: String): UserNotificationDto {
        return api.get(id)
    }

    override suspend fun save(userNotificationDto: UserNotificationDto) {
        api.save(userNotificationDto)
    }

    override suspend fun delete(id: String) {
        api.delete(id)
    }
}