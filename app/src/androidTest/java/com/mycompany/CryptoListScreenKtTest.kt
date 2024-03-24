package com.mycompany.currencytracker.presentation.common.crypto

import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mycompany.currencytracker.presentation.MainActivity
import com.mycompany.currencytracker.presentation.calculator.ui.elements.CryptoListItem
import com.mycompany.currencytracker.presentation.crypto_list.CryptoListState
import com.mycompany.currencytracker.presentation.crypto_list.CryptoListViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CryptoListScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun cryptoList_DisplayedSuccessfully() {
        val viewModel = mockk<CryptoListViewModel>()
        every { viewModel.state } returns mutableStateOf(CryptoListState(cryptos = emptyList()))

        composeTestRule.activity.setContent {
            CryptoListScreen(haveHeader = true, viewModel = viewModel) { crypto ->
                CryptoListItem(crypto = crypto, onItemClick = {})
            }
        }

        composeTestRule.onNodeWithText("Name").assertIsDisplayed()
        composeTestRule.onNodeWithText("Price").assertIsDisplayed()
        composeTestRule.onNode(hasTestTag("CryptoItem"))
    }
    @Test
    fun cryptoList_DisplayedError() {
        val viewModel = mockk<CryptoListViewModel>()
        every { viewModel.state } returns mutableStateOf(CryptoListState(error = "an unexpected error occurred"))

        composeTestRule.activity.setContent {
            CryptoListScreen(haveHeader = true, viewModel = viewModel) { crypto ->
                CryptoListItem(crypto = crypto, onItemClick = {})
            }
        }

        composeTestRule.onNodeWithText("an unexpected error occurred").assertIsDisplayed()
    }
}
