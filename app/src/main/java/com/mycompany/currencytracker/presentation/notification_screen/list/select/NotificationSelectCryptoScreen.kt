package com.mycompany.currencytracker.presentation.notification_screen.list.select

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.domain.model.user.notification.CryptoSelectNotification
import com.mycompany.currencytracker.presentation.common.AutoResizedText
import com.mycompany.currencytracker.presentation.common.currency.CurrencyListSearchState
import com.mycompany.currencytracker.presentation.common.list.ItemsListScreen
import com.mycompany.currencytracker.presentation.common.search.SearchBar
import com.mycompany.currencytracker.presentation.navigation.Screen


@Composable
fun NotificationSelectCryptoScreen(navController: NavController) {
    val viewModel = hiltViewModel<NotificationSelectCryptoViewModel>()
    val state = viewModel.state

    var searchText by remember {
        mutableStateOf("")
    }
    val searchState = CurrencyListSearchState(searchText) {
        searchText = it
    }

    Column {
        ItemsListScreen(
            header = {
                Box(Modifier.padding(16.dp)) {
                    SearchBar(searchState)
                }
                HorizontalDivider(color = Color.Transparent, thickness = 27.dp)
                Box(Modifier.padding(start = 16.dp)) {
                    Text(
                        text = stringResource(id = R.string.all_coins),
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 22.sp,
                            fontWeight = FontWeight(400),
                            color = Color.White
                        )
                    )
                }
            },
            stateValue = state.value,
            list = viewModel.searchResult.value,
            onListRefresh = viewModel::getItems
        ) { item, _ ->
            SelectCryptoNotificationItem(item) {
                navController.navigate(Screen.NotificationScreen.route + "/${item.symbol}")
            }
            HorizontalDivider(modifier = Modifier.padding(top = 16.dp))
        }
    }
    LaunchedEffect(key1 = searchText) {
        viewModel.search(searchText)
    }
}


@Composable
fun SelectCryptoNotificationItem(
    crypto: CryptoSelectNotification,
    onItemClick: (CryptoSelectNotification) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .padding(top = 16.dp, start = 20.dp)
            .clickable { onItemClick(crypto) },

        ) {
        AsyncImage(
            modifier = Modifier
                .height(24.dp)
                .width(24.dp)
                .fillMaxWidth()
                .weight(0.5f),
            model = crypto.image,
            contentDescription = crypto.symbol + " img"
        )
        AutoResizedText(
            text = crypto.symbol.uppercase(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .weight(1f),
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 22.sp,
                fontWeight = FontWeight(400),
                color = Color.White,
            )
        )
        Text(
            modifier = Modifier.weight(5f).fillMaxWidth(),
            text = crypto.name,
            textAlign = TextAlign.Start,
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 22.sp,
                fontWeight = FontWeight(400),
                color = Color.Gray,
            )
        )


    }
}


@Preview
@Composable
private fun SelectCryptoNotificationItemPrev() {
    SelectCryptoNotificationItem(
        CryptoSelectNotification(
            symbol = "BTC",
            name = "Bitcoin",
            image = "https://s2.coinmarketcap.com/static/img/coins/200x200/1.png"
        )
    ) {

    }
}