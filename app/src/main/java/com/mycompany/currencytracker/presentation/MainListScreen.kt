package com.mycompany.currencytracker.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.mycompany.currencytracker.presentation.common.currency.ListScreen
import com.mycompany.currencytracker.presentation.common.currency.crypto.CryptoListScreen
import com.mycompany.currencytracker.presentation.common.currency.fiat.FiatListScreen
import com.mycompany.currencytracker.presentation.crypto_list.components.CryptoListItem
import com.mycompany.currencytracker.presentation.currency_list.components.CurrencyListItem
import com.mycompany.currencytracker.presentation.ui.theme.selectTextColor

@Composable
fun MainListScreen() {
    ListScreen(
        fiatListScreen = {
            FiatListScreen(haveHeader = true) { currencyItem, currNumber ->
                CurrencyListItem(currencyItem, currNumber) {

                }
            }
        }, cryptoListScreen = {
            CryptoListScreen(
                haveHeader = true
            ) { crypto ->
                CryptoListItem(crypto = crypto, number = crypto.rank) {

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