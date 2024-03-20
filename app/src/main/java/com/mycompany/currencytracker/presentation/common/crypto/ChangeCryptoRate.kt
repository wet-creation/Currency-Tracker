package com.mycompany.currencytracker.presentation.common.crypto

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.mycompany.currencytracker.domain.model.currency.crypto.CryptoGeneralInfo
import com.mycompany.currencytracker.presentation.ui.theme.rateDownColor
import com.mycompany.currencytracker.presentation.ui.theme.rateUpColor
import com.mycompany.currencytracker.presentation.ui.theme.secondTextColor
import kotlin.math.floor

@Composable
fun ChangeCryptoRate(
    crypto: CryptoGeneralInfo,
    time: Int = 24
) {
    val pastRate: Double? = when (time) {
        24 -> crypto._24h
        7 -> crypto._7d
        30 -> crypto._30d
        else -> null
    }

    pastRate?.let {
        if (crypto.rate >= it) {
            Icon(
                modifier = Modifier
                    .padding(end = 0.dp)
                    .rotate(180f),
                imageVector = Icons.Rounded.ArrowDropDown,
                contentDescription = "currDown",
                tint = rateUpColor
            )
            Text(
                text = "${String.format("%.2f", (crypto.rate / pastRate - 1) * 100)}%",
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
                text = "${String.format("%.2f", (pastRate / crypto.rate - 1) * 100)}%",
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

fun calculateMarketCap(marketCap: Long): String {
    return when {
        marketCap >= 1000000000 -> "${floor(marketCap / 1000000000f * 1000f) / 1000f} B"
        marketCap >= 1000000 -> "${floor(marketCap / 1000000f * 1000f) / 1000f} M"
        else -> "$marketCap"
    }
}