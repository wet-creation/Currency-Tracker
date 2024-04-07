package com.mycompany.currencytracker.presentation.favorite_list

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mycompany.currencytracker.presentation.common.currency.CurrenciesListHeader
import com.mycompany.currencytracker.presentation.common.currency.CurrencyListsScreen
import com.mycompany.currencytracker.presentation.common.list.ItemsListScreen
import com.mycompany.currencytracker.presentation.crypto_list.components.CryptoListItem
import com.mycompany.currencytracker.presentation.currency_list.components.CurrencyListItem
import com.mycompany.currencytracker.presentation.navigation.Screen
import com.mycompany.currencytracker.presentation.ui.theme.selectTextColor

@Composable
fun FavoriteListScreen(navController: NavHostController) {

    val cryptoListViewModel = hiltViewModel<FavoriteCryptoListViewModel>()
    val fiatListViewModel = hiltViewModel<FavoriteFiatListViewModel>()

    CurrencyListsScreen(
        fiatListScreen = {
            ItemsListScreen(
                header = { CurrenciesListHeader() },
                viewModel = fiatListViewModel,
                list = fiatListViewModel.state.value.currencies
            ) { currencyItem, currNumber ->
                CurrencyListItem(currencyItem, currNumber) {
                    navController.navigate(Screen.CurrencyDetailScreen.route + "/${currencyItem.symbol}")
                }
            }
        }, cryptoListScreen = {
            ItemsListScreen(
                header = { CurrenciesListHeader() },
                viewModel = cryptoListViewModel,
                list = cryptoListViewModel.state.value.currencies
            ) { crypto, _ ->
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