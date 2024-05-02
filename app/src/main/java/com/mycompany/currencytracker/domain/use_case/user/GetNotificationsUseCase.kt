package com.mycompany.currencytracker.domain.use_case.user

import com.mycompany.currencytracker.common.DataError
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.domain.model.user.notification.UserNotification
import com.mycompany.currencytracker.domain.repository.CryptosRepository
import com.mycompany.currencytracker.domain.repository.UserNotificationRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(
    private val repositoryNotification: UserNotificationRepository,
    private val repositoryCrypto: CryptosRepository
) {
    operator fun invoke(email: String, baseCurrency: String = "USD") =
        flow<Resource<List<UserNotification>, DataError.Network>> {
            try {
                emit(Resource.Loading())
                val notificationDto = repositoryNotification.getAll(email, baseCurrency)
                val cryptoMap = repositoryCrypto.getLatest().associateBy { it.symbol }
                val intersectingNotifications = notificationDto.mapNotNull { notification ->
                    cryptoMap[notification.symbol]?.let { crypto ->
                        UserNotification(
                            id = notification.id,
                            userId = notification.userId,
                            symbol = notification.symbol,
                            target = notification.target,
                            isMoreThanTarget = notification.isMoreThanTarget,
                            isConstantly = notification.isConstantly,
                            image = crypto.image,
                            marketCapRank = crypto.market_cap_rank,
                            name = crypto.name
                        )
                    }
                }
                emit(Resource.Success(intersectingNotifications.sortedBy { it.marketCapRank }))
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

            }
        }
}