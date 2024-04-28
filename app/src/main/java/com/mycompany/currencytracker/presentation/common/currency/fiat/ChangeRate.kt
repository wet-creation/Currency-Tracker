package com.mycompany.currencytracker.presentation.common.currency.fiat

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mycompany.currencytracker.domain.model.currency.IChangeRates
import com.mycompany.currencytracker.presentation.common.currency.crypto.cryptoTest
import com.mycompany.currencytracker.presentation.ui.theme.rateDownColor
import com.mycompany.currencytracker.presentation.ui.theme.rateUpColor
import com.mycompany.currencytracker.presentation.ui.theme.secondTextColor


@Preview
@Composable
fun ChangeRate(
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
            Row {
                Icon(
                    modifier = Modifier
                        .padding(end = 0.dp)
                        .rotate(180f),
                    imageVector = Icons.Rounded.ArrowDropDown,
                    contentDescription = "currDown",
                    tint = rateUpColor
                )
                Text(
                    text = "${String.format("%.2f", (currencyRate.rate / pastRate - 1) * 100)}%",
                    style = MaterialTheme.typography.bodyLarge,
                    color = rateUpColor
                )
            }

        } else {
            Row{
                Icon(
                    modifier = Modifier.padding(end = 0.dp),
                    imageVector = Icons.Rounded.ArrowDropDown,
                    contentDescription = "currDown",
                    tint = rateDownColor
                )
                Text(
                    text = "${String.format("%.2f", (pastRate / currencyRate.rate - 1) * 100)}%",
                    style = MaterialTheme.typography.bodyLarge,
                    color = rateDownColor
                )
            }
        }

        return
    }
    Text(
        text = "-",
        style = MaterialTheme.typography.bodyLarge,
        color = secondTextColor
    )

}