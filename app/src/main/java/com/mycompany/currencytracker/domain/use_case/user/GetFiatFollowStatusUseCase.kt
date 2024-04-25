package com.mycompany.currencytracker.domain.use_case.user

import com.mycompany.currencytracker.common.DataError
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.domain.model.user.FollowedFiat
import com.mycompany.currencytracker.domain.model.user.toFiatFollowed
import com.mycompany.currencytracker.domain.repository.UserFollowedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetFiatFollowStatusUseCase @Inject constructor(
    private val userFollowedRepository: UserFollowedRepository
) {
    operator fun invoke(symbol: String, userId: String): Flow<Resource<FollowedFiat, DataError.Network>> = flow {
        try {
            emit(Resource.Loading())
            val fiat = userFollowedRepository.getFiatFollowStatus(symbol, userId)
            emit(Resource.Success(fiat.toFiatFollowed()))
        } catch (e: HttpException) {
            when (e.code()) {
                500 -> emit(Resource.Error(DataError.Network.SERVER_ERROR))
                404 -> emit(Resource.Error(DataError.Network.NOT_FOUND))
                else -> emit(Resource.Error(DataError.Network.UNKNOWN))
            }
        } catch (e: IOException) {
            emit(Resource.Error(DataError.Network.NO_INTERNET))
        }
    }
}