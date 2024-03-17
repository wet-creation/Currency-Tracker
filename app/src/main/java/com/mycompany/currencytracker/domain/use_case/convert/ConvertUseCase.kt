package com.mycompany.currencytracker.domain.use_case.convert

import com.mycompany.currencytracker.common.Resource
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
    operator fun invoke(from: String, to: String, value: Double = 0.0): Flow<Resource<Convert>> = flow {
        try {
            emit(Resource.Loading())
            val converted = repository.convert(value, from, to).toConvert()
            emit(Resource.Success(converted))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "error"))
        } catch (e: IOException) {
            emit(Resource.Error("Check your internet connection"))
        }
    }
}