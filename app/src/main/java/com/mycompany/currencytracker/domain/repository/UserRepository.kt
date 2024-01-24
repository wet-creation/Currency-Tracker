package com.mycompany.currencytracker.domain.repository

import com.mycompany.currencytracker.data.remote.dto.user.UserDto
import com.mycompany.currencytracker.data.remote.dto.user.UserLoginDto

interface UserRepository {

    suspend fun register(userDto: UserDto)
    suspend fun getUser(email: String): UserDto
    suspend fun login(userLoginDto: UserLoginDto): UserDto

}