package com.mycompany.currencytracker.domain.use_case.currency

import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.domain.model.currency.fiat.FiatAdditionalInfo
import com.mycompany.currencytracker.domain.repository.CurrenciesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetFiatAdditionalInfoUseCase @Inject constructor(
    private val repository: CurrenciesRepository
) {
    operator fun invoke(symbol: String, baseCurrency: String = "USD"): Flow<Resource<FiatAdditionalInfo>> =
        flow {
            try {
                emit(Resource.Loading())
                val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                val currentDate = LocalDate.now().minusDays(1).format(formatter)

                val fiat24hResponse = repository.getHistoricalByOneDate(currentDate, symbol, baseCurrency)
                val currencyResponse = repository.getLatestBySymbol(symbol, baseCurrency)

                val open = fiat24hResponse.first().rate
                val close = fiat24hResponse.last().rate
                val high24 = fiat24hResponse.maxOf { it.rate }
                val low24 = fiat24hResponse.minOf { it.rate }
                val avg = fiat24hResponse.sumOf { it.rate } / fiat24hResponse.count()

                val fiatInfo = FiatAdditionalInfo(high24, low24, avg, open, close, currencyResponse.symbol, currencyResponse.name, currencyResponse.rate, currencyResponse._24h, currencyResponse._7d, currencyResponse._30d)

                emit(Resource.Success(fiatInfo))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "error"))
            } catch (e: IOException) {
                emit(Resource.Error("Check your internet connection"))
            }
        }
}