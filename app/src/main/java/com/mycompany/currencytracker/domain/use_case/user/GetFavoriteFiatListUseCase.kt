package com.mycompany.currencytracker.domain.use_case.user

import com.mycompany.currencytracker.common.DataError
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.domain.model.user.followed.FollowedFiat
import com.mycompany.currencytracker.domain.model.user.followed.toFiatFollowed
import com.mycompany.currencytracker.domain.repository.UserFollowedRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetFavoriteFiatListUseCase @Inject constructor(
    val userFollowedRepository: UserFollowedRepository
) {
    operator fun invoke(userId: String, baseCurrency : String) = flow<Resource<List<FollowedFiat>, DataError.Network>> {
        try {
            emit(Resource.Loading())
            val fiat = userFollowedRepository.getFollowedFiat(userId, baseCurrency)
            emit(Resource.Success(fiat.map { it.toFiatFollowed() }.sortedBy { it.numberInList }))
        } catch (e: HttpException) {
            when (e.code()) {
                408 -> emit(Resource.Error(DataError.Network.REQUEST_TIMEOUT))
                413 -> emit(Resource.Error(DataError.Network.PAYLOAD_TOO_LARGE))
                503 -> emit(Resource.Error(DataError.Network.SERVER_ERROR))
                404 -> emit(Resource.Error(DataError.Network.NOT_FOUND))
                400 -> emit(Resource.Error(DataError.Network.BAD_REQUEST))
                409 -> emit(Resource.Error(DataError.Network.CONFLICT))
                else -> emit(Resource.Error(DataError.Network.UNKNOWN))
            }

        } catch (e: IOException) {
            emit(Resource.Error(DataError.Network.NO_INTERNET))
        }
    }
}