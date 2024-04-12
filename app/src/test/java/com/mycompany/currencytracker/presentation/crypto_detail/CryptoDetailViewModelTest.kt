package com.mycompany.currencytracker.presentation.crypto_detail

import androidx.lifecycle.SavedStateHandle
import com.mycompany.currencytracker.common.Constants
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.domain.model.currency.crypto.CryptoDetails
import com.mycompany.currencytracker.domain.use_case.crypto.GetCryptoDetailsUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class CryptoDetailViewModelTest {
    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    @Test
    fun `verify data loading on viewModel initialization`() {
        // Arrange
        val mockGetCryptoDetailsUseCase = mockk<GetCryptoDetailsUseCase>(relaxed = true)
        val mockStoreUserSetting = mockk<StoreUserSetting>(relaxed = true)
        val savedStateHandle = SavedStateHandle(mapOf(Constants.PARAM_COIN_ID to "btc"))

        every { mockGetCryptoDetailsUseCase(any(), any()) } returns flow {
            emit(Resource.Success(CryptoDetails()))
        }

        // Act
        val viewModel = CryptoDetailViewModel(mockGetCryptoDetailsUseCase, savedStateHandle, mockStoreUserSetting)

        // Assert
        assertNotNull(viewModel.state.value.crypto)
        verify(exactly = 2) { mockGetCryptoDetailsUseCase(any(), any()) }
    }

}