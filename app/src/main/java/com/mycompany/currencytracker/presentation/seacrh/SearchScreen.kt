package com.mycompany.currencytracker.presentation.seacrh

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
import com.mycompany.currencytracker.domain.model.currency.fiat.FiatDetails
import com.mycompany.currencytracker.presentation.common.currency.ListScreen
import com.mycompany.currencytracker.presentation.common.currency.SearchPosition
import com.mycompany.currencytracker.presentation.common.currency.StateListScreen
import com.mycompany.currencytracker.presentation.common.currency.crypto.CryptoListScreen
import com.mycompany.currencytracker.presentation.common.currency.crypto.CryptoSearchListViewModel
import com.mycompany.currencytracker.presentation.common.currency.fiat.FiatListScreen
import com.mycompany.currencytracker.presentation.common.currency.fiat.FiatSearchListViewModel
import com.mycompany.currencytracker.presentation.crypto_list.components.CryptoListItem
import com.mycompany.currencytracker.presentation.currency_list.components.CurrencyListItem
import com.mycompany.currencytracker.presentation.ui.theme.selectTextColor

@Composable
fun SearchScreen() {

    val fiatSearchListViewModel = hiltViewModel<FiatSearchListViewModel>()
    val cryptoSearchListViewModel = hiltViewModel<CryptoSearchListViewModel>()
    var stateQuery by remember {
        mutableStateOf("")
    }
    ListScreen(
        searchPosition = SearchPosition.Top,
        StateListScreen(stateQuery) {
            stateQuery = it
        },
        fiatListScreen = {
            FiatListScreen(haveHeader = true, viewModel = fiatSearchListViewModel) { currencyItem: FiatDetails, currNumber: Int ->
                CurrencyListItem (fiatDetails = currencyItem, currNumber = currNumber) {

                }
            }
        },
        cryptoListScreen = {
            CryptoListScreen(haveHeader = true, viewModel = cryptoSearchListViewModel) { crypto ->
                CryptoListItem (crypto = crypto) {

                }
            }
        }) {
        Text(
            text = it,
            style = TextStyle(
                fontSize = 16.sp,
                color = selectTextColor,
            )
        )
    }

    LaunchedEffect(key1 = stateQuery) {
        cryptoSearchListViewModel.search(stateQuery)
        fiatSearchListViewModel.search(stateQuery)
    }
}