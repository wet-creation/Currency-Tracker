package com.mycompany.currencytracker.presentation.seacrh

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mycompany.currencytracker.domain.model.currency.fiat.FiatDetails
import com.mycompany.currencytracker.presentation.common.currency.CurrenciesListHeader
import com.mycompany.currencytracker.presentation.common.currency.CurrencyListSearchState
import com.mycompany.currencytracker.presentation.common.currency.CurrencyListsScreen
import com.mycompany.currencytracker.presentation.common.currency.crypto.CryptoListItem
import com.mycompany.currencytracker.presentation.common.currency.crypto.CryptoSearchListViewModel
import com.mycompany.currencytracker.presentation.common.currency.fiat.FiatListItem
import com.mycompany.currencytracker.presentation.common.currency.fiat.FiatSearchListViewModel
import com.mycompany.currencytracker.presentation.common.list.ItemsListScreen
import com.mycompany.currencytracker.presentation.common.search.SearchPosition
import com.mycompany.currencytracker.presentation.navigation.Screen

@Composable
fun SearchScreen(
    navController: NavController
) {
    val fiatSearchListViewModel = hiltViewModel<FiatSearchListViewModel>()
    val cryptoSearchListViewModel = hiltViewModel<CryptoSearchListViewModel>()
    val userSetting = cryptoSearchListViewModel.userSetting

    var stateQuery by remember {
        mutableStateOf("")
    }
    CurrencyListsScreen(
        searchPosition = SearchPosition.Top,
        currencyListSearchState = CurrencyListSearchState(stateQuery) {
            stateQuery = it
        },
        fiatListScreen = {
            ItemsListScreen(
                header = { CurrenciesListHeader(userSetting) },
                stateValue = fiatSearchListViewModel.state.value,
                list = fiatSearchListViewModel.searchResult.value,
                onListRefresh = fiatSearchListViewModel::getItems
            ) { currencyItem: FiatDetails, currNumber: Int ->
                FiatListItem(fiatDetails = currencyItem, currNumber = currNumber, dataStore = userSetting) {
                    navController.navigate(Screen.CurrencyDetailScreen.route + "/${currencyItem.symbol}")
                }
            }
        },
        cryptoListScreen = {
            ItemsListScreen(
                header = { CurrenciesListHeader(userSetting) },
                stateValue = cryptoSearchListViewModel.state.value,
                list = cryptoSearchListViewModel.searchResult.value,
                onListRefresh = cryptoSearchListViewModel::getItems
            ) { crypto, _ ->
                CryptoListItem(crypto = crypto, number = crypto.rank, dataStore = userSetting) {
                    navController.navigate(Screen.CryptoDetailScreen.route + "/${crypto.symbol}")
                }
            }
        }) {
        Text(
            text = it,
            style = TextStyle(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.outline,
            )
        )
    }

    LaunchedEffect(key1 = stateQuery) {
        cryptoSearchListViewModel.search(stateQuery)
        fiatSearchListViewModel.search(stateQuery)
    }
}