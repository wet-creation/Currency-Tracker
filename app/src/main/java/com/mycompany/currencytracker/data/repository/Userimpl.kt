package com.mycompany.currencytracker.data.repository

import com.mycompany.currencytracker.data.remote.dto.user.UserDto
import com.mycompany.currencytracker.data.remote.dto.user.UserLoginDto
import com.mycompany.currencytracker.data.remote.services.user.UserService
import com.mycompany.currencytracker.domain.repository.UserRepository
import javax.inject.Inject

class Userimpl @Inject constructor(
    val api: UserService
) : UserRepository {
    override suspend fun register(userDto: UserDto) {
        api.register(userDto)
    }

    override suspend fun getUser(email: String): UserDto {
        return api.getUser(email)
    }

    override suspend fun login(userLoginDto: UserLoginDto): UserDto {
        return api.login(userLoginDto)
    }
}