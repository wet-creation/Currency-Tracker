package com.mycompany.currencytracker.presentation.setting_screen.currency_select_screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.presentation.common.currency.ListScreen
import com.mycompany.currencytracker.presentation.common.currency.crypto.CryptoListScreen
import com.mycompany.currencytracker.presentation.common.currency.fiat.FiatListScreen
import com.mycompany.currencytracker.presentation.setting_screen.currency_select_screen.crypto.CryptoSelectListItem
import com.mycompany.currencytracker.presentation.setting_screen.currency_select_screen.сurrency.CurrencySelectListItem
import com.mycompany.currencytracker.presentation.ui.theme.selectTextColor
import kotlinx.coroutines.launch

@Composable
fun CurrencySelectScreen() {
    val scope = rememberCoroutineScope()
    val dataStore = StoreUserSetting(LocalContext.current)

    ListScreen(
        fiatListScreen = {
            FiatListScreen(
                haveHeader = false
            ) { currencyItem, _ ->
                CurrencySelectListItem(currencyItem) {
                    scope.launch {
                        dataStore.saveCurrency(currencyItem.symbol)
                    }
                }
            }
        }, cryptoListScreen = {
            CryptoListScreen(
                haveHeader = false
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
}