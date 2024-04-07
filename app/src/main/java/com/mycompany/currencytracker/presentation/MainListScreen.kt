package com.mycompany.currencytracker.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mycompany.currencytracker.presentation.common.ListScreen
import com.mycompany.currencytracker.presentation.common.crypto.CryptoListScreen
import com.mycompany.currencytracker.presentation.common.fiat.FiatListScreen
import com.mycompany.currencytracker.presentation.crypto_list.components.CryptoListItem
import com.mycompany.currencytracker.presentation.currency_list.components.CurrencyListItem
import com.mycompany.currencytracker.presentation.navigation.Screen
import com.mycompany.currencytracker.presentation.ui.theme.selectTextColor

@Composable
fun MainListScreen(
    navController: NavController
) {
    ListScreen(
        fiatListScreen = {
            FiatListScreen(haveHeader = true) { currencyItem, currNumber ->
                CurrencyListItem(currencyItem, currNumber) {
                    navController.navigate(Screen.CurrencyDetailScreen.route + "/${currencyItem.symbol}")
                }
            }
        }, cryptoListScreen = {
            CryptoListScreen(
                haveHeader = true
            ) { crypto ->
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