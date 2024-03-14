package com.mycompany.currencytracker.domain.use_case.crypto

import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.domain.model.currency.crypto.CryptoGeneralInfo
import com.mycompany.currencytracker.domain.model.currency.crypto.toCryptoGeneralInfo
import com.mycompany.currencytracker.domain.repository.CryptosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTop100RateUseCase @Inject constructor(
    private val cryptosRepository: CryptosRepository
) {
    operator fun invoke(baseCurrency : String = "USD"): Flow<Resource<List<CryptoGeneralInfo>>> = flow {
        try {
            emit(Resource.Loading())
            val cryptos = cryptosRepository.getLatest(baseCurrency)
            emit(Resource.Success(cryptos.map { it.toCryptoGeneralInfo() }))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "error"))
        } catch (e: IOException) {
            emit(Resource.Error("Check your internet connection"))
        }
    }
}