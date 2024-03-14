package com.mycompany.currencytracker.domain.use_case.currency

import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.domain.model.currency.fiat.FiatDetails
import com.mycompany.currencytracker.domain.model.currency.fiat.toCurrency
import com.mycompany.currencytracker.domain.repository.CurrenciesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCurrencyOrCurrenciesRatesByDateUseCase @Inject constructor(
    private val repository: CurrenciesRepository
) {
    operator fun invoke(date: String, symbol: String? = null): Flow<Resource<List<FiatDetails>>> =
        flow {
            try {
                emit(Resource.Loading())
                val currencyResponse = repository.getHistoricalByOneDate(date, symbol)
                emit(Resource.Success(currencyResponse.map { it.toCurrency() }))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "error"))
            } catch (e: IOException) {
                emit(Resource.Error("Check your internet connection"))
            }
        }
}