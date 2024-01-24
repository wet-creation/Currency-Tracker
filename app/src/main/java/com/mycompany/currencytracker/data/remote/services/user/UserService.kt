package com.mycompany.currencytracker.data.remote.services.user

import com.mycompany.currencytracker.data.remote.dto.user.UserDto
import com.mycompany.currencytracker.data.remote.dto.user.UserLoginDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {

    @POST("register/save")
    suspend fun register(@Body userDto: UserDto)
    @GET("user")
    suspend fun getUser(@Query("email") email: String): UserDto
    @POST("user/login")
    suspend fun login(@Body userLoginDto: UserLoginDto): UserDto
}