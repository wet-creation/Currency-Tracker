package com.mycompany.currencytracker.domain.use_case.user

import com.mycompany.currencytracker.common.DataError
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.domain.model.user.notification.UserNotification
import com.mycompany.currencytracker.domain.repository.ConvertRepository
import com.mycompany.currencytracker.domain.repository.UserNotificationRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SaveNotificationUseCase @Inject constructor(
    private val notificationRepository: UserNotificationRepository,
    private val convertor: ConvertRepository,
) {
    operator fun invoke(userNotificationSave: UserNotification, baseCurrency: String) =
        flow<Resource<Unit, DataError.Network>> {
            try {
                emit(Resource.Loading())
                val convertDto = convertor.convert(userNotificationSave.target, baseCurrency, "USD")
                val saveNotification = UserNotification(
                    userNotificationSave.id,
                    userNotificationSave.userId,
                    userNotificationSave.symbol,
                    convertDto.response,
                    userNotificationSave.isMoreThanTarget,
                    userNotificationSave.isConstantly,
                    "",
                    0,
                    ""
                )

                notificationRepository.save(saveNotification.toUserNotificationSaveDto())
                emit(Resource.Success(Unit))
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