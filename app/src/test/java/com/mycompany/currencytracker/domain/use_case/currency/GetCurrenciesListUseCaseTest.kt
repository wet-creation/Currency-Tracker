package com.mycompany.currencytracker.domain.use_case.currency

import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.data.remote.dto.currency.fiat.CurrencyDto
import com.mycompany.currencytracker.domain.model.currency.fiat.toCurrency
import com.mycompany.currencytracker.domain.repository.CurrenciesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class GetCurrenciesListUseCaseTest {

    private lateinit var getCurrenciesListUseCase: GetCurrenciesListUseCase
    private val mockRepository: CurrenciesRepository = mockk()

    @Before
    fun setUp() {
        getCurrenciesListUseCase = GetCurrenciesListUseCase(mockRepository)
    }

    @Test
    fun `invoke returns success when repository fetches data successfully`() = runTest {
        // Given
        val fakeCurrencyList = listOf(CurrencyDto(1, "USD", 1234567890L, 1.0, "Dollar", 0.1, 0.2, 0.3))
        val expected = fakeCurrencyList.map { it.toCurrency() }
        coEvery { mockRepository.getLatest(any()) } returns fakeCurrencyList

        // When
        val result = getCurrenciesListUseCase("USD").toList()

        // Then
        assertEquals(2, result.size)
        assert(result[0] is Resource.Loading)
        assert(result[1] is Resource.Success)
        assertEquals(expected, (result[1] as Resource.Success).data)
    }

    @Test
    fun `invoke returns error on IOException`() = runTest {
        // Given
        coEvery { mockRepository.getLatest(any()) } throws IOException()

        // When
        val result = getCurrenciesListUseCase("USD").toList()

        // Then
        assertEquals(2, result.size)
        assert(result[0] is Resource.Loading)
        assert(result[1] is Resource.Error)
        assertEquals("Check your internet connection", (result[1] as Resource.Error).message)
    }

    @Test
    fun `invoke returns error on HttpException`() = runTest {
        // Given
        val errorMessage = "404"
        coEvery { mockRepository.getLatest(any()) } throws HttpException(Response.error<Any>(404, ResponseBody.create(null, "")))

        // When
        val result = getCurrenciesListUseCase("USD").toList()

        // Then
        assertEquals(2, result.size)
        assert(result[0] is Resource.Loading)
        assert(result[1] is Resource.Error)
        assertEquals(errorMessage, (result[1].message))
    }
}
