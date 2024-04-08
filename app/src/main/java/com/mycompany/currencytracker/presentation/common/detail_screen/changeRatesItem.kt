package com.mycompany.currencytracker.presentation.common.detail_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mycompany.currencytracker.domain.model.currency.IChangeRates
import com.mycompany.currencytracker.presentation.common.currency.fiat.ChangeRate
import com.mycompany.currencytracker.presentation.ui.theme.mainTextColor

@Composable
fun ChangeRatesItem(currency: IChangeRates) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 6.dp),
                text = "24H",
                style = MaterialTheme.typography.bodyLarge,
                color = mainTextColor
            )
            HorizontalDivider()
            Box(modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp)) {
                ChangeRate(fiatDetails = currency, 24)
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 6.dp),
                text = "7D",
                style = MaterialTheme.typography.bodyLarge,
                color = mainTextColor
            )
            HorizontalDivider()
            Box(modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp)) {
                ChangeRate(fiatDetails = currency, 7)
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 6.dp),
                text = "30D",
                style = MaterialTheme.typography.bodyLarge,
                color = mainTextColor
            )
            HorizontalDivider()
            Box(modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp)) {
                ChangeRate(fiatDetails = currency, 30)
            }
        }
    }
}