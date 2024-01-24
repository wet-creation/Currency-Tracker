package com.mycompany.currencytracker.domain.repository

import com.mycompany.currencytracker.data.remote.dto.user.UserNotificationDto

interface UserNotificationRepository {

    suspend fun getAll(email: String): List <UserNotificationDto>

    suspend fun get(id: String): UserNotificationDto

    suspend fun save(userNotificationDto: UserNotificationDto)

    suspend fun delete(id: String)
}