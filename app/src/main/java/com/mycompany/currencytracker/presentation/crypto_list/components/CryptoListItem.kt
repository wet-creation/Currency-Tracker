package com.mycompany.currencytracker.presentation.crypto_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mycompany.currencytracker.domain.model.currency.crypto.CryptoGeneralInfo
import com.mycompany.currencytracker.presentation.ui.theme.mainTextColor
import com.mycompany.currencytracker.presentation.ui.theme.rateDownColor
import com.mycompany.currencytracker.presentation.ui.theme.rateUpColor
import com.mycompany.currencytracker.presentation.ui.theme.secondTextColor
import kotlin.math.floor

@Composable
fun CryptoListItem(
    crypto: CryptoGeneralInfo,
    number: Int = 1,
    onItemClick: (crypto: CryptoGeneralInfo) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .width(349.dp)
            .height(43.dp)
            .clickable { onItemClick(crypto) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            modifier = Modifier.weight(0.9f),
            text = "$number",
            style = MaterialTheme.typography.bodyLarge,
            color = secondTextColor
        )
        Row(modifier = Modifier.weight(2.4f)) {
            AsyncImage(
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .clip(CircleShape),
                model = crypto.image,
                contentDescription = "image description",
                contentScale = ContentScale.Crop
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(-1.dp, Alignment.Top),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    text = crypto.symbol.uppercase(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = mainTextColor
                )
                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    text = calculateMarketCap(crypto.marketCap),
                    style = TextStyle(
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight(300),
                        fontSize = 10.sp,
                        lineHeight = 22.sp,
                        letterSpacing = 0.5.sp
                    ),
                    color = mainTextColor
                )
            }

        }
        Text(
            modifier = Modifier
                .weight(3f)
                .padding(horizontal = 10.dp),
            text = when {
                crypto.rate >= 1 -> "$${String.format("%.2f", crypto.rate)}"
                crypto.rate >= 0.0001 -> "$${String.format("%.4f", crypto.rate)}"
                crypto.rate >= 0.00000001 -> "$${String.format("%.8f", crypto.rate)}"
                else -> "$0.00000000"
            },
            style = MaterialTheme.typography.bodyLarge,
            color = mainTextColor,
            textAlign = TextAlign.End
        )
        Row(
            horizontalArrangement = Arrangement.End
        ) {
            ChangeCryptoRate(crypto = crypto)
        }
    }
}

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