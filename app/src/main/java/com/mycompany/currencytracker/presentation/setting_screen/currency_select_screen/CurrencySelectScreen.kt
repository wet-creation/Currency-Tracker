package com.mycompany.currencytracker.presentation.setting_screen.currency_select_screen

import androidx.compose.material3.MaterialTheme
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
import com.mycompany.currencytracker.presentation.common.currency.CurrencyListSearchState
import com.mycompany.currencytracker.presentation.common.currency.CurrencyListsScreen
import com.mycompany.currencytracker.presentation.common.currency.crypto.CryptoSearchListViewModel
import com.mycompany.currencytracker.presentation.common.currency.fiat.FiatSearchListViewModel
import com.mycompany.currencytracker.presentation.common.list.ItemsListScreen
import com.mycompany.currencytracker.presentation.common.search.SearchPosition
import com.mycompany.currencytracker.presentation.setting_screen.currency_select_screen.crypto.CryptoSelectListItem
import com.mycompany.currencytracker.presentation.setting_screen.currency_select_screen.сurrency.CurrencySelectListItem
import kotlinx.coroutines.launch

@Composable
fun CurrencySelectScreen() {
    val fiatSearchListViewModel = hiltViewModel<FiatSearchListViewModel>()
    val cryptoSearchListViewModel = hiltViewModel<CryptoSearchListViewModel>()
    val scope = rememberCoroutineScope()
    val dataStore = StoreUserSetting(LocalContext.current)
    var searchQuery by remember { mutableStateOf("") }

    CurrencyListsScreen(
        searchPosition = SearchPosition.InBetween,
        currencyListSearchState = CurrencyListSearchState(searchQuery) {
            searchQuery = it
        },
        fiatListScreen = {
            ItemsListScreen(
                stateValue = fiatSearchListViewModel.state.value,
                list = fiatSearchListViewModel.searchResult.value,
                onListRefresh = fiatSearchListViewModel::getItems
            ) { currencyItem, _ ->
                CurrencySelectListItem(currencyItem) {
                    scope.launch {
                        dataStore.saveCurrency(currencyItem.symbol)
                    }
                }
            }
        }, cryptoListScreen = {
            ItemsListScreen(
                stateValue = cryptoSearchListViewModel.state.value,
                list = cryptoSearchListViewModel.searchResult.value,
                onListRefresh = cryptoSearchListViewModel::getItems
            ) { crypto, _ ->
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
                color = MaterialTheme.colorScheme.outline,
            )
        )

    }
    LaunchedEffect(key1 = searchQuery) {
        debugLog("search $searchQuery")
        fiatSearchListViewModel.search(searchQuery)
        cryptoSearchListViewModel.search(searchQuery)
    }
}