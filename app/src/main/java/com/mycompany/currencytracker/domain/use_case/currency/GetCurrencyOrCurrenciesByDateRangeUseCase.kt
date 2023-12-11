package com.mycompany.currencytracker.domain.use_case.currency

import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.domain.model.currency.Currency
import com.mycompany.currencytracker.domain.model.currency.toCurrancyList
import com.mycompany.currencytracker.domain.repository.CurrencyTrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCurrencyOrCurrenciesByDateRangeUseCase @Inject constructor(
    private val repository: CurrencyTrackerRepository
) {
    operator fun invoke(
        startDate: String,
        endDate: String,
        symbol: String? = null
    ): Flow<Resource<List<Currency>>> =
        flow {
            try {
                emit(Resource.Loading())
                val currencyRate = repository.getHistoricalByDateRange(startDate, endDate, symbol)
                val currencyName = repository.getCurrenciesName(symbol)
                emit(Resource.Success(toCurrancyList(currencyRate, currencyName)))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "error"))
            } catch (e: IOException) {
                emit(Resource.Error("Check your internet connection"))
            }
        }
}