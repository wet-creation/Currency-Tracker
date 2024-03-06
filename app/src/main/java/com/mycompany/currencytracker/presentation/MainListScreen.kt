package com.mycompany.currencytracker.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.Divider
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.presentation.crypto_list.CryptoListScreen
import com.mycompany.currencytracker.presentation.currency_list.CurrencyListScreen
import com.mycompany.currencytracker.presentation.ui.theme.selectTextColor

@Composable
fun MainListScreen() {
    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Fiat", "Crypto")

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            modifier = Modifier
                .size(height = 22.dp, width = 106.dp)
                .padding(start = 20.dp),
            selectedTabIndex = tabIndex
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    modifier = Modifier.height(height = 22.dp),
                    text = {
                        Text(
                            text = title,
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = selectTextColor,
                            )
                        )
                    },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )

            }
        }
        Divider()
        when (tabIndex) {
            0 -> CurrencyListScreen()
            1 -> CryptoListScreen()
        }
    }

}