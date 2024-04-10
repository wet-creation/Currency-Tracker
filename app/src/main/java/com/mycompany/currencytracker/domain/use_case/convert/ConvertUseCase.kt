package com.mycompany.currencytracker.domain.use_case.convert

import com.mycompany.currencytracker.common.DataError
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.common.debugLog
import com.mycompany.currencytracker.domain.model.currency.Convert
import com.mycompany.currencytracker.domain.repository.ConvertRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ConvertUseCase @Inject constructor(
    private val repository: ConvertRepository
) {
    operator fun invoke(
        from: String,
        to: String,
        value: Double = 0.0
    ): Flow<Resource<Convert, DataError>> = flow {
        try {
            emit(Resource.Loading())
            debugLog("Convert $from to $to")
            val converted = repository.convert(value, from, to).toConvert()
            emit(Resource.Success(converted))
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