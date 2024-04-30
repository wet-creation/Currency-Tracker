package com.mycompany.currencytracker.presentation.fiat_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.presentation.common.currency.fiat.ChangeRate
import com.mycompany.currencytracker.presentation.common.detail_screen.ChangeChartTimeButtons
import com.mycompany.currencytracker.presentation.common.detail_screen.ChangeRatesItem
import com.mycompany.currencytracker.presentation.common.detail_screen.Chart
import com.mycompany.currencytracker.presentation.fiat_detail.components.FiatDetailInfo
import com.mycompany.currencytracker.presentation.navigation.DetailTopBar

@Composable
fun CurrencyDetailScreen(
    viewModel: FiatDetailViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val state = viewModel.state.value

    val context = LocalContext.current
    val dataStore = StoreUserSetting(context)

    val savedCurrency = dataStore.getFiat.collectAsState(initial = "")

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        state.currency?.let { currency ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp)
            ) {
                item{
                    val follow = viewModel.followStatus.value

                    DetailTopBar(navController, currency.symbol, follow){
                        if(!follow){
                            viewModel.addFiatToFavoriteList(currency.symbol)
                        } else {
                            viewModel.removeFiatFromFavoriteList(currency.symbol)
                        }
                    }
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${savedCurrency.value} ${String.format("%.6f", currency.rate)}",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Row(modifier = Modifier.padding(start = 5.dp, top = 10.dp)) {
                            ChangeRate(currencyRate = currency)
                        }
                    }
                }
                item {
                    Chart(viewModel.graphInfo.value)
                    ChangeChartTimeButtons(dataStore.getChartTime()
                    ) { time ->
                        viewModel.changeChartTime(time)
                    }
                }
                item {
                    ChangeRatesItem(currency = currency)
                }
                item {
                    FiatDetailInfo(currency = currency, savedCurrency.value)
                }
            }

        }

        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}