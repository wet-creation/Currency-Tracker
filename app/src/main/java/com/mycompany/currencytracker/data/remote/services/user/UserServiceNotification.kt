package com.mycompany.currencytracker.data.remote.services.user

import com.mycompany.currencytracker.data.remote.dto.user.UserNotificationDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserServiceNotification {
    @GET("user/notifications")
    suspend fun getAll(@Query("email") email: String): List <UserNotificationDto>
    @GET("user/notifications/id")
    suspend fun get(@Path("id") id: String): UserNotificationDto
    @POST("user/notification/save")
    suspend fun save(@Body userNotificationDto: UserNotificationDto)
    @DELETE("user/notification/delete")
    suspend fun delete(@Path("userNotificationId") id: String)
}