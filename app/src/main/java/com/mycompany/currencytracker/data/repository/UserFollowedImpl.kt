package com.mycompany.currencytracker.data.repository

import com.mycompany.currencytracker.data.remote.dto.user.CryptoFollowedDto
import com.mycompany.currencytracker.data.remote.dto.user.CryptoFollowedListDto
import com.mycompany.currencytracker.data.remote.dto.user.CurrencyFollowedList
import com.mycompany.currencytracker.data.remote.dto.user.FiatFollowedDto
import com.mycompany.currencytracker.data.remote.services.user.UserServiceFollowed
import com.mycompany.currencytracker.domain.repository.UserFollowedRepository
import javax.inject.Inject

class UserFollowedImpl @Inject constructor(
    val api: UserServiceFollowed
): UserFollowedRepository {
    override suspend fun followedCrypto(cryptoFollowedListDto: CryptoFollowedListDto) {
        api.followedCrypto(cryptoFollowedListDto)
    }

    override suspend fun followedFiat(fiatFollowedList: CurrencyFollowedList) {
        api.followedFiat(fiatFollowedList)
    }

    override suspend fun deleteCryptoFollowed(userId: String, symbol: String) {
        api.deleteCryptoFollowed(userId, symbol)
    }

    override suspend fun deleteFiatFollowed(userId: String, symbol: String) {
        api.deleteFiatFollowed(userId, symbol)
    }

    override suspend fun getFollowedCrypto(
        userID: String,
        baseCurrency: String
    ): List<CryptoFollowedDto> {
        return api.getFollowedCrypto(userID)
    }

    override suspend fun getFollowedFiat(
        userID: String,
        baseCurrency: String
    ): List<FiatFollowedDto> {
        return api.getFollowedFiat(userID)
    }

   // TODO() добавить на сервер получение по айди продукта добавлен ли он в список желаемого

}