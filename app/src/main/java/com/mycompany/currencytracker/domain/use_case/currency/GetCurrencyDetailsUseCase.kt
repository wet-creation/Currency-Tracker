package com.mycompany.currencytracker.domain.use_case.currency

import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.domain.model.currency.Currency
import com.mycompany.currencytracker.domain.repository.CurrencyTrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCurrencyDetailsUseCase @Inject constructor(
    private val repository: CurrencyTrackerRepository
) {
    operator fun invoke(
        symbol: String
    ): Flow<Resource<Currency>> = flow {
        try {
            emit(Resource.Loading())
            val currencyRate = repository.getLatestBySymbol(symbol)
            val currencyName = repository.getCurrenciesName(symbol)[0]
            val currency = Currency(currencyName.symbol, currencyName.name, currencyRate.timestamp, currencyRate.rate, currencyRate.id)
            emit(Resource.Success(currency))

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "error"))
        } catch (e: IOException) {
            emit(Resource.Error("Check your internet connection"))
        }
    }
}