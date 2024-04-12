package com.mycompany.currencytracker.domain.repository

import com.mycompany.currencytracker.data.remote.dto.user.CryptoFollowedDto
import com.mycompany.currencytracker.data.remote.dto.user.CryptoFollowedListDto
import com.mycompany.currencytracker.data.remote.dto.user.CurrencyFollowedList
import com.mycompany.currencytracker.data.remote.dto.user.FiatFollowedDto

interface UserFollowedRepository {
    suspend fun followedCrypto( cryptoFollowedListDto: CryptoFollowedListDto)
    suspend fun followedFiat( fiatFollowedList: CurrencyFollowedList)
    suspend fun deleteCryptoFollowed(userId: String, symbol: String)
    suspend fun deleteFiatFollowed(userId: String,  symbol: String)
    suspend fun getFollowedCrypto(userID: String, baseCurrency: String = "USD"): List<CryptoFollowedDto>
    suspend fun getFollowedFiat(userID: String, baseCurrency: String = "USD"): List<FiatFollowedDto>

}
