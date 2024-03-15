package com.mycompany.currencytracker.presentation.setting_screen.currency_select_screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mycompany.currencytracker.common.debugLog
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.presentation.common.currency.ListScreen
import com.mycompany.currencytracker.presentation.common.currency.SearchPosition
import com.mycompany.currencytracker.presentation.common.currency.StateListScreen
import com.mycompany.currencytracker.presentation.common.currency.crypto.CryptoListScreen
import com.mycompany.currencytracker.presentation.common.currency.crypto.CryptoSearchListViewModel
import com.mycompany.currencytracker.presentation.common.currency.fiat.FiatListScreen
import com.mycompany.currencytracker.presentation.common.currency.fiat.FiatSearchListViewModel
import com.mycompany.currencytracker.presentation.setting_screen.currency_select_screen.crypto.CryptoSelectListItem
import com.mycompany.currencytracker.presentation.setting_screen.currency_select_screen.сurrency.CurrencySelectListItem
import com.mycompany.currencytracker.presentation.ui.theme.selectTextColor
import kotlinx.coroutines.launch

@Composable
fun CurrencySelectScreen() {
    val currencySearchListViewModel = hiltViewModel<FiatSearchListViewModel>()
    val cryptoSearchListViewModel = hiltViewModel<CryptoSearchListViewModel>()
    val scope = rememberCoroutineScope()
    val dataStore = StoreUserSetting(LocalContext.current)
    var searchQuery by remember { mutableStateOf("") }

    ListScreen(
        searchPosition = SearchPosition.InBetween,
        stateListScreen = StateListScreen(searchQuery) {
            searchQuery = it
        },
        fiatListScreen = {
            FiatListScreen(
                haveHeader = false,
                viewModel = currencySearchListViewModel
            ) { currencyItem, _ ->
                CurrencySelectListItem(currencyItem) {
                    scope.launch {
                        dataStore.saveCurrency(currencyItem.symbol)
                    }
                }
            }
        }, cryptoListScreen = {
            CryptoListScreen(
                haveHeader = false,
                viewModel = cryptoSearchListViewModel
            ) { crypto ->
                CryptoSelectListItem(crypto = crypto) {
                    scope.launch {
                        dataStore.saveCrypto(crypto.symbol)
                    }
                }
            }
        })
    { title ->
        Text(
            text = title,
            style = TextStyle(
                fontSize = 16.sp,
                color = selectTextColor,
            )
        )

    }
    LaunchedEffect(key1 = searchQuery) {
        debugLog("search $searchQuery")
        currencySearchListViewModel.search(searchQuery)
        cryptoSearchListViewModel.search(searchQuery)
    }
}