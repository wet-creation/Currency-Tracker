package com.mycompany.currencytracker.presentation.crypto_detail.components

import androidx.compose.foundation.layout.Arrangement
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
import com.mycompany.currencytracker.domain.model.currency.crypto.CryptoDetails
import com.mycompany.currencytracker.presentation.common.AutoResizedText
import com.mycompany.currencytracker.presentation.common.currency.crypto.calculateDecimalPlaces
import com.mycompany.currencytracker.presentation.common.currency.crypto.calculateDigit
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
fun CryptoDetailInfo(crypto: CryptoDetails, baseCurrency: String) {
    Column(modifier = Modifier.padding(top = 20.dp)) {
        TableItem(name = stringResource(id = R.string.crypto_rank), value = crypto.rank.toString())
        HorizontalDivider()
        TableItem(name = stringResource(id = R.string.market_cap), value = baseCurrency + " " + calculateDigit(crypto.marketCap))
        HorizontalDivider()
        TableItem(name = stringResource(id = R.string.total_supply), value = calculateDigit(crypto.totalSupply?.toLong() ?: 0))
        HorizontalDivider()
        TableItem(name = stringResource(id = R.string.max_supply), value = calculateDigit(crypto.maxSupply?.toLong() ?: 0))
        HorizontalDivider()
        TableItem(name = stringResource(id = R.string.circulating_supply), value = calculateDigit(crypto.circulatingSupply.toLong()))
        HorizontalDivider()
        TableItemAllTimeValue(name = stringResource(id = R.string.ath), value = baseCurrency + " " + calculateDecimalPlaces(crypto.ath), time = crypto.athTimestamp * 1000)
        HorizontalDivider()
        TableItemAllTimeValue(name = stringResource(id = R.string.atl), value = baseCurrency + " " + calculateDecimalPlaces(crypto.atl), time = crypto.atlTimestamp * 1000)

    }
}

@Composable
fun TableItem(name: String, value: String) {
    Row(
        modifier = Modifier
            .height(52.dp)
            .fillMaxWidth()
            .padding(start = 20.dp, top = 15.dp, bottom = 15.dp, end = 15.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1.5f).fillMaxWidth()
        ){
            AutoResizedText(
                style = MaterialTheme.typography.bodyLarge,
                text = name,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f).fillMaxWidth()
        ){
            AutoResizedText(
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.align(Alignment.End),
                text = value,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun TableItemAllTimeValue(name: String, value: String, time: Long) {
    Row(
        modifier = Modifier
            .height(73.dp)
            .fillMaxWidth()
            .padding(start = 20.dp, top = 15.dp, bottom = 15.dp, end = 15.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1.5f).fillMaxWidth()
        ){
            Text(
                style = MaterialTheme.typography.bodyLarge,
                text = name,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f).fillMaxWidth()
        ){
            AutoResizedText(
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.align(Alignment.End),
                text = value,
                color = MaterialTheme.colorScheme.secondary
            )
            AutoResizedText(
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.align(Alignment.End).padding(top = 3.dp),
                text = formatTimestampWithDaysSince(time),
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

fun formatTimestampWithDaysSince(timestamp: Long): String {
    val date = LocalDate.ofEpochDay(timestamp / (24 * 60 * 60 * 1000))
    val now = LocalDate.now()
    val daysBetween = ChronoUnit.DAYS.between(date, now)
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val formattedDate = date.format(formatter)

    return "$formattedDate ($daysBetween days)"
}
