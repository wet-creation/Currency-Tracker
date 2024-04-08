package com.mycompany.currencytracker.domain.use_case.currency

import co.yml.charts.common.model.Point
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.domain.repository.CurrenciesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class GetFiatGraphInfoUseCase @Inject constructor(
    private val repository: CurrenciesRepository
) {
    operator fun invoke(
        timestamp : Int = 24,
        symbol: String,
        baseCurrency: String = "USD"
    ): Flow<Resource<Map<Long, Point>>> = flow {
        try {
            emit(Resource.Loading())

            val now = LocalDateTime.now()

            val period = when (timestamp) {
                24 -> now.minusHours(24)
                7 -> now.minusDays(7)
                30 -> now.minusDays(30)
                else -> now
            }

            val timeRes = period.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() / 1000
            val currencyResponse = repository.getPeriod(timeRes, symbol, baseCurrency)

            val partSize = Math.ceil(currencyResponse.size / 10.0).toInt()

            val ratesAverageArray = mutableMapOf<Long, Point>()

            for (i in 0 until 10) {
                val fromIndex = i * partSize
                val toIndex = Math.min(fromIndex + partSize, currencyResponse.size)

                if (fromIndex < toIndex) {
                    val average = currencyResponse.subList(fromIndex, toIndex).map { it.rate }.average()
                    val time = currencyResponse[toIndex - 1].timestamp
                    val point = Point(i.toFloat(), average.toFloat())

                    ratesAverageArray[time] = point
                }
            }

            emit(Resource.Success(ratesAverageArray))

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "error"))
        } catch (e: IOException) {
            emit(Resource.Error("Check your internet connection"))
        }
    }
}