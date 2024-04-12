package com.mycompany.currencytracker.presentation.favorite_list.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.presentation.common.SwipeToDeleteContainer
import com.mycompany.currencytracker.presentation.common.auth.PleaseLoginScreen
import com.mycompany.currencytracker.presentation.common.currency.CurrenciesListHeader
import com.mycompany.currencytracker.presentation.common.currency.CurrencyListsScreen
import com.mycompany.currencytracker.presentation.common.currency.crypto.CryptoListItem
import com.mycompany.currencytracker.presentation.common.currency.fiat.FiatListItem
import com.mycompany.currencytracker.presentation.common.list.ItemsListScreen
import com.mycompany.currencytracker.presentation.favorite_list.FavoriteCryptoListViewModel
import com.mycompany.currencytracker.presentation.favorite_list.FavoriteFiatListViewModel
import com.mycompany.currencytracker.presentation.navigation.Screen
import com.mycompany.currencytracker.presentation.ui.theme.selectTextColor

@Composable
fun FavoriteListScreen(navController: NavHostController) {
    val cryptoListViewModel = hiltViewModel<FavoriteCryptoListViewModel>()
    val fiatListViewModel = hiltViewModel<FavoriteFiatListViewModel>()
    val userSetting = StoreUserSetting(LocalContext.current)

    if (userSetting.getUser().email != "") {
        CurrencyListsScreen(
            fiatListScreen = {
                ItemsListScreen(
                    header = { CurrenciesListHeader() },
                    stateValue = fiatListViewModel.state.value,
                    list = fiatListViewModel.state.value.items,
                    onListRefresh = fiatListViewModel::getItems
                ) { currencyItem, currNumber ->
                    FiatListItem(currencyItem, currNumber) {
                        navController.navigate(Screen.CurrencyDetailScreen.route + "/${currencyItem.symbol}")
                    }
                }
            }, cryptoListScreen = {
                ItemsListScreen(
                    header = { CurrenciesListHeader() },
                    stateValue = cryptoListViewModel.state.value,
                    list = cryptoListViewModel.state.value.items,
                    onListRefresh = cryptoListViewModel::getItems,
                    key = {_, item -> item.symbol}
                ) { crypto, currNumber ->
                    SwipeToDeleteContainer(
                        item = crypto,
                        onDelete =  cryptoListViewModel::delete
                    ) {
                        CryptoListItem(crypto = it, number = currNumber) {
                            navController.navigate(Screen.CryptoDetailScreen.route + "/${crypto.symbol}")
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
    } else {
        PleaseLoginScreen(navController)
    }
}
