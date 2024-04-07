package com.mycompany.currencytracker.presentation.crypto_detail

import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mycompany.currencytracker.domain.model.currency.crypto.CryptoDetails
import com.mycompany.currencytracker.presentation.MainActivity
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CryptoDetailScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun cryptoDetailScreen_loadingState_displayedCorrectly() {
        val viewModel = mockk<CryptoDetailViewModel>()
        every { viewModel.state } returns mutableStateOf(CryptoDetailState(error = "an unexpected error occurred"))
        every { viewModel.fiatRate } returns mutableStateOf(null)

        composeTestRule.activity.setContent {
            CryptoDetailScreen(viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("an unexpected error occurred").assertIsDisplayed()
    }

    @Test
    fun cryptoDetailScreen_displaysLoadingStateInitially() {
        val viewModel = mockk<CryptoDetailViewModel>()
        every { viewModel.state } returns mutableStateOf(CryptoDetailState(isLoading = true))
        every { viewModel.fiatRate } returns mutableStateOf(null)

        composeTestRule.activity.setContent {
            CryptoDetailScreen(viewModel = viewModel)
        }

        composeTestRule.onNode(hasTestTag("LoadingIndicator")).assertIsDisplayed()
    }
    @Test
    fun cryptoDetailScreen_displaysCryptoDataWhenLoaded() {
        val viewModel = mockk<CryptoDetailViewModel>()
        every { viewModel.state } returns mutableStateOf(CryptoDetailState(crypto = CryptoDetails()))
        every { viewModel.fiatRate } returns mutableStateOf(null)


        composeTestRule.activity.setContent  {
            CryptoDetailScreen(viewModel = viewModel)
        }

        composeTestRule.onNode(hasTestTag("CryptoElement")).assertIsDisplayed()
    }

}

