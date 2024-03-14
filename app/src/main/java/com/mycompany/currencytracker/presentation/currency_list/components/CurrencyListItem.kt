package com.mycompany.currencytracker.presentation.currency_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mycompany.currencytracker.common.Constants.image_url
import com.mycompany.currencytracker.domain.model.currency.fiat.Currency

import com.mycompany.currencytracker.presentation.ui.theme.mainTextColor
import com.mycompany.currencytracker.presentation.ui.theme.rateDownColor
import com.mycompany.currencytracker.presentation.ui.theme.rateUpColor
import com.mycompany.currencytracker.presentation.ui.theme.secondTextColor

val exampleCurrency = Currency(
    symbol = "USD",
    name = "US Dollar",
    timestamp = System.currentTimeMillis(),
    rate = 1.0,
    id = 1L,
    _24h = 0.5,
    _7d = 2.0,
    _30d = -1.0
)


@Composable
fun CurrencyListItem(
    currency: Currency = exampleCurrency,
    curr_number: Int = 1,
    onItemClick: (Currency) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable{onItemClick(currency)}
            .padding(20.dp)
            .width(349.dp)
            .height(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.weight(0.9f),
            text = "$curr_number",
            style = MaterialTheme.typography.bodyLarge,
            color = secondTextColor
        )
        Row(modifier = Modifier.weight(2f)) {
            AsyncImage(
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .clip(CircleShape),
                model = image_url + currency.symbol.lowercase() + ".png",
                contentDescription = "image description",
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier.padding(start = 12.dp),
                text = "${currency.symbol}",
                style = MaterialTheme.typography.bodyLarge,
                color = mainTextColor
            )
        }
        Text(
            modifier = Modifier
                .weight(3f)
                .padding(horizontal = 10.dp),
            text = when {
                currency.rate >= 1 -> "$${String.format("%.2f", currency.rate)}"
                currency.rate >= 0.0001 -> "$${String.format("%.4f", currency.rate)}"
                currency.rate >= 0.00000001 -> "$${String.format("%.8f", currency.rate)}"
                else -> "$0.00000000"
            },
            style = MaterialTheme.typography.bodyLarge,
            color = mainTextColor,
            textAlign = TextAlign.End
        )
        Row(
            horizontalArrangement = Arrangement.End
        ) {
            ChangeRate(currency = currency)
        }
    }
}

@Composable
fun ChangeRate(
    currency: Currency,
    time: Int = 24
) {
    var change = 0.00
    var pastRate: Double?
    when (time) {
        24 -> pastRate = currency._24h
        7 -> pastRate = currency._7d
        30 -> pastRate = currency._30d
        else -> pastRate = null
    }

    pastRate?.let {
        if (currency.rate >= it) {
            Icon(
                modifier = Modifier
                    .padding(end = 0.dp)
                    .rotate(180f),
                imageVector = Icons.Rounded.ArrowDropDown,
                contentDescription = "currDown",
                tint = rateUpColor
            )
            Text(
                text = "${String.format("%.2f", (currency.rate / pastRate - 1) * 100)}%",
                style = MaterialTheme.typography.bodyLarge,
                color = rateUpColor
            )
        } else {
            Icon(
                modifier = Modifier.padding(end = 0.dp),
                imageVector = Icons.Rounded.ArrowDropDown,
                contentDescription = "currDown",
                tint = rateDownColor
            )
            Text(
                text = "${String.format("%.2f", (pastRate / currency.rate - 1) * 100)}%",
                style = MaterialTheme.typography.bodyLarge,
                color = rateDownColor
            )
        }

        return
    }
    Text(
        text = "-",
        style = MaterialTheme.typography.bodyLarge,
        color = secondTextColor
    )

}