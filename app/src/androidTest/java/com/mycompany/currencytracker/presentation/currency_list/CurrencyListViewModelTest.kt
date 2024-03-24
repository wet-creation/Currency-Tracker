package com.mycompany.currencytracker.presentation.currency_list

import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.data.remote.dto.currency.fiat.CurrencyDto
import com.mycompany.currencytracker.domain.repository.CurrenciesRepository
import com.mycompany.currencytracker.domain.use_case.currency.GetCurrenciesListUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException

@RunWith(JUnit4::class)
class CurrencyListViewModelTest {

    private lateinit var getCurrenciesListUseCase: GetCurrenciesListUseCase
    private lateinit var userSettings: StoreUserSetting
    private lateinit var currenciesRepository: CurrenciesRepository

    private lateinit var viewModel: CurrencyListViewModel

    @Before
    fun setup() {
        currenciesRepository = mockk()
        getCurrenciesListUseCase = GetCurrenciesListUseCase(currenciesRepository)
        userSettings = mockk()

        every { userSettings.getCurrency() } returns "USD"

        viewModel = CurrencyListViewModel(getCurrenciesListUseCase, userSettings)
    }

    @Test
    fun whenGetCurrenciesListUseCaseReturnsSuccessStateContainsCurrencies() = runTest {
        val fakeCurrencies = listOf(CurrencyDto(1,"USD", 121212, 1.0,  "Dollar", 1.0, 1.0, 1.0))
        coEvery { currenciesRepository.getLatest("USD") } returns fakeCurrencies
        val state = viewModel.state.value
        viewModel.getCurrencies()

        Assert.assertTrue(state.isLoading)
    }

    @Test
    fun whenGetCurrenciesListUseCaseReturnsErrorStateContainsCurrencies() = runTest {
        val errorMessage = "Network Error"
        coEvery { currenciesRepository.getLatest(any()) } throws IOException(errorMessage)

        viewModel.getCurrencies()

        assert(viewModel.state.value.error == errorMessage)
    }
}