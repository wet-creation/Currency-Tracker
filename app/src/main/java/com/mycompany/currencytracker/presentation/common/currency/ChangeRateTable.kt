package com.mycompany.currencytracker.presentation.common.currency

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.domain.model.currency.IChangeRates
import com.mycompany.currencytracker.presentation.common.currency.crypto.cryptoTest
import com.mycompany.currencytracker.presentation.ui.theme.rateDownColor
import com.mycompany.currencytracker.presentation.ui.theme.rateUpColor


@Preview
@Composable
fun ChangeRateTable(
    modifier: Modifier = Modifier,
    currencyRate: IChangeRates = cryptoTest,
    time: String = "24h"
) {
    val pastRate: Double? = when (time) {
        "24h" -> currencyRate._24h
        "7d" -> currencyRate._7d
        "30d" -> currencyRate._30d
        else -> null
    }

    pastRate?.let {
        if (currencyRate.rate >= it) {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f),
                    painter = painterResource(id = R.drawable.arrow_up_icon),
                    alignment = Alignment.CenterEnd,
                    contentDescription = "currDown"
                )
                Text(
                    text = "${String.format("%.2f", (currencyRate.rate / pastRate - 1) * 100)}%",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodyLarge,
                    color = rateUpColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.5f),
                )
            }

        } else {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f),
                    painter = painterResource(id = R.drawable.arrow_down_icon),
                    alignment = Alignment.CenterEnd,
                    contentDescription = "currDown",
                )
                Text(
                    textAlign = TextAlign.End,
                    text = "${String.format("%.2f", (pastRate / currencyRate.rate - 1) * 100)}%",
                    style = MaterialTheme.typography.bodyLarge,
                    color = rateDownColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.5f),
                )
            }
        }

        return
    }
    Text(
        text = "-",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.secondary
    )

}

@Composable
fun ChangeRate(
    modifier: Modifier = Modifier,
    currencyRate: IChangeRates = cryptoTest,
    time: String = "24h"
) {
    val pastRate: Double? = when (time) {
        "24h" -> currencyRate._24h
        "7d" -> currencyRate._7d
        "30d" -> currencyRate._30d
        else -> null
    }

    pastRate?.let {
        if (currencyRate.rate >= it) {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_up_icon),
                    alignment = Alignment.CenterEnd,
                    contentDescription = "currDown"
                )
                Text(
                    text = "${String.format("%.2f", (currencyRate.rate / pastRate - 1) * 100)}%",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodyLarge,
                    color = rateUpColor,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

        } else {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_down_icon),
                    alignment = Alignment.CenterEnd,
                    contentDescription = "currDown",
                )
                Text(
                    textAlign = TextAlign.End,
                    text = "${String.format("%.2f", (pastRate / currencyRate.rate - 1) * 100)}%",
                    style = MaterialTheme.typography.bodyLarge,
                    color = rateDownColor,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }

        return
    }
    Text(
        text = "-",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.secondary
    )

}