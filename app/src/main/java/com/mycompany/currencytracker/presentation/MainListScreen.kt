package com.mycompany.currencytracker.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mycompany.currencytracker.presentation.common.currency.CurrenciesListHeader
import com.mycompany.currencytracker.presentation.common.currency.CurrencyListsScreen
import com.mycompany.currencytracker.presentation.common.currency.crypto.CryptoListItem
import com.mycompany.currencytracker.presentation.common.currency.fiat.FiatListItem
import com.mycompany.currencytracker.presentation.common.list.ItemsListScreen
import com.mycompany.currencytracker.presentation.crypto_list.CryptoListViewModel
import com.mycompany.currencytracker.presentation.fiat_list.CurrencyListViewModel
import com.mycompany.currencytracker.presentation.navigation.Screen
import com.mycompany.currencytracker.presentation.ui.theme.selectTextColor

@Composable
fun MainListScreen(
    navController: NavController
) {
    val fiatSearchListViewModel = hiltViewModel<CurrencyListViewModel>()
    val cryptoSearchListViewModel = hiltViewModel<CryptoListViewModel>()
    CurrencyListsScreen(
        fiatListScreen = {
            ItemsListScreen(
                header = { CurrenciesListHeader() },
                stateValue = fiatSearchListViewModel.state.value,
                list = fiatSearchListViewModel.state.value.items,
                key = {_, item -> item.id},
                onListRefresh = fiatSearchListViewModel::getItems
            ) { currencyItem, currNumber ->
                FiatListItem(currencyItem, currNumber) {
                    navController.navigate(Screen.CurrencyDetailScreen.route + "/${currencyItem.symbol}")
                }
            }
        }, cryptoListScreen = {
            ItemsListScreen(
                header = { CurrenciesListHeader(true, cryptoSearchListViewModel::getItems) },
                list = cryptoSearchListViewModel.state.value.items,
                stateValue = cryptoSearchListViewModel.state.value,
                key = {_, item -> item.id},
                onListRefresh = cryptoSearchListViewModel::getItems
            ) { crypto, _->
                CryptoListItem(crypto = crypto, number = crypto.rank) {
                    navController.navigate(Screen.CryptoDetailScreen.route + "/${crypto.symbol}")
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
}