package com.mycompany.currencytracker.presentation.crypto_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.domain.model.currency.crypto.CryptoDetails
import com.mycompany.currencytracker.presentation.common.currency.crypto.calculateDecimalPlaces
import com.mycompany.currencytracker.presentation.common.detail_screen.ChangeRatesItem
import com.mycompany.currencytracker.presentation.common.detail_screen.Chart
import com.mycompany.currencytracker.presentation.common.currency.fiat.ChangeRate
import com.mycompany.currencytracker.presentation.crypto_detail.components.CryptoDetailInfo
import com.mycompany.currencytracker.presentation.ui.theme.mainTextColor
import com.mycompany.currencytracker.presentation.ui.theme.secondTextColor

@Composable
fun CryptoDetailScreen(
    viewModel: CryptoDetailViewModel = hiltViewModel(),
) {

    val state = viewModel.state.value

    val context = LocalContext.current
    val dataStore = StoreUserSetting(context)

    val savedCrypto = dataStore.getCrypto.collectAsState(initial = "")
    val savedCurrency = dataStore.getCurrency.collectAsState(initial = "")

    val selectedCurrency = savedCrypto
    val mainCurrency: CryptoDetails?
    val secondRate: String

    if (selectedCurrency == savedCrypto){
        mainCurrency = state.crypto
        secondRate = (savedCurrency.value + " " + calculateDecimalPlaces(viewModel.fiatRate.value?.rate ?: 0.0))
    }
    else{
        mainCurrency = viewModel.fiatRate.value
        secondRate = (savedCrypto.value.uppercase() + " " + calculateDecimalPlaces(state.crypto?.rate ?: 0.0))
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        mainCurrency?.let { crypto ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = selectedCurrency.value.uppercase() + " " + calculateDecimalPlaces(crypto.rate),
                            style = MaterialTheme.typography.displayLarge
                        )
                        Row(modifier = Modifier.padding(start = 5.dp, top = 5.dp)) {
                            ChangeRate(crypto)
                        }
                    }
                    Row(modifier = Modifier.padding(top = 8.dp)) {
                        Icon(
                            painterResource(id = R.drawable.change_values_icon),
                            modifier = Modifier
                                .rotate(90f)
                                .padding(end = 9.dp, top = 6.dp)
                                .width(15.dp)
                                .height(15.dp),
                            contentDescription = "change_values",
                            tint = mainTextColor
                        )
                        Text(
                            text = secondRate,
                            style = MaterialTheme.typography.displaySmall,
                            color = secondTextColor
                        )
                    }
                }
                item {
                    Chart()
                }
                item {
                    ChangeRatesItem(crypto)
                }
                item {
                    CryptoDetailInfo(crypto, selectedCurrency.value.uppercase())
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