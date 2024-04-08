package com.mycompany.currencytracker.domain.use_case.currency

import com.mycompany.currencytracker.common.DataError
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.domain.model.currency.fiat.FiatDetails
import com.mycompany.currencytracker.domain.model.currency.fiat.toCurrency
import com.mycompany.currencytracker.domain.repository.CurrenciesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCurrencyDetailsUseCase @Inject constructor(
    private val repository: CurrenciesRepository
) {
    operator fun invoke(
        symbol: String,
        baseCurrency: String = "USD"
    ): Flow<Resource<FiatDetails, DataError.Network>> = flow {
        try {
            emit(Resource.Loading())
            val currencyResponse = repository.getLatestBySymbol(symbol, baseCurrency)
            emit(Resource.Success(currencyResponse.toCurrency()))

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