package com.mycompany.currencytracker.data.remote.services.user

import com.mycompany.currencytracker.data.remote.dto.user.CryptoFollowedDto
import com.mycompany.currencytracker.data.remote.dto.user.CryptoFollowedListDto
import com.mycompany.currencytracker.data.remote.dto.user.CurrencyFollowedList
import com.mycompany.currencytracker.data.remote.dto.user.FiatFollowedDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserServiceFollowed {

    @POST("user/cryptolist")
    suspend fun followedCrypto(@Body cryptoFollowedListDto: CryptoFollowedListDto)
    @DELETE("user/cryptolist/delete")
    suspend fun deleteCryptoFollowed(@Query("userId") userId: String, @Query("symbol") symbol: String)
    @POST("user/fiatlist")
    suspend fun followedFiat(@Body fiatFollowedList: CurrencyFollowedList)
    @DELETE("user/fiatlist/delete")
    suspend fun deleteFiatFollowed(@Query("userId") userId: String, @Query("symbol") symbol: String)
    @GET("user/followed/crypto")
    suspend fun getFollowedCrypto(@Query("userId") id: String): List<CryptoFollowedDto>
    @GET("user/followed/fiat")
    suspend fun getFollowedFiat(@Query("userId") id: String): List<FiatFollowedDto>

}