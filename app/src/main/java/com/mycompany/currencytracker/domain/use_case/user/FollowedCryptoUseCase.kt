package com.mycompany.currencytracker.domain.use_case.user

import com.mycompany.currencytracker.common.DataError
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.domain.model.user.followed.FollowedCrypto
import com.mycompany.currencytracker.domain.repository.UserFollowedRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FollowedCryptoUseCase @Inject constructor(
    val userFollowedRepository: UserFollowedRepository
) {
    operator fun invoke(crypto: FollowedCrypto) = flow<Resource<Unit, DataError.Network>> {
        try {
            emit(Resource.Loading())
            userFollowedRepository.followedCrypto(crypto.toCryptoFollowedListDto())
            emit(Resource.Success(Unit))
        } catch (e: HttpException) {
            when (e.code()) {
                404 -> emit(Resource.Error(DataError.Network.NOT_FOUND))
                500 -> emit(Resource.Error(DataError.Network.SERVER_ERROR))
            }
        } catch (e: IOException) {
            emit(Resource.Error(DataError.Network.NO_INTERNET))
        }

    }
}