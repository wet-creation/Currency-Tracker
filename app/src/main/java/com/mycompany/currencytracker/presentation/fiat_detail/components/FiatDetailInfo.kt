package com.mycompany.currencytracker.presentation.fiat_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.domain.model.currency.fiat.FiatAdditionalInfo
import com.mycompany.currencytracker.presentation.ui.theme.mainTextColor
import com.mycompany.currencytracker.presentation.ui.theme.secondTextColor

@Composable
fun FiatDetailInfo(currency: FiatAdditionalInfo, baseCurrency: String) {
    Column(modifier = Modifier.padding(top = 20.dp)) {
        TableItem(name = stringResource(id = R.string._24h_max), value = currency.high24, baseCurrency)
        HorizontalDivider()
        TableItem(name = stringResource(id = R.string._24h_low), value = currency.low24, baseCurrency)
        HorizontalDivider()
        TableItem(name = stringResource(id = R.string.fiat_open), value = currency.open, baseCurrency)
        HorizontalDivider()
        TableItem(name = stringResource(id = R.string.fiat_close), value = currency.close, baseCurrency)
        HorizontalDivider()
        TableItem(name = stringResource(id = R.string.average), value = currency.averageRate, baseCurrency)

    }
}

@Composable
fun TableItem(name: String, value: Double, baseCurrency: String) {
    Row(
        modifier = Modifier
            .height(52.dp)
            .fillMaxWidth()
            .padding(start = 20.dp, top = 15.dp, bottom = 15.dp, end = 15.dp)
    ) {
        Column(
           modifier = Modifier.weight(1f)
        ){
            Text(
                style = MaterialTheme.typography.bodyLarge,
                text = name,
                color = mainTextColor
            )
        }
        Column(
            modifier = Modifier.weight(1f)
        ){
            Text(
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.align(Alignment.End),
                text = baseCurrency + " " + String.format("%.6f", value),
                color = secondTextColor
            )
        }
    }
}
