package com.mycompany.currencytracker.domain.repository

import com.mycompany.currencytracker.data.remote.dto.user.UserNotificationDto
import com.mycompany.currencytracker.data.remote.dto.user.UserNotificationSaveDto

interface UserNotificationRepository {


    suspend fun get(id: String): UserNotificationDto

    suspend fun save(userNotificationDto: UserNotificationSaveDto)

    suspend fun delete(id: String)
    suspend fun getAll(email: String, baseCurrency: String): List<UserNotificationDto>
}