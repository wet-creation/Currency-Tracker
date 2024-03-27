package com.mycompany.currencytracker.presentation.currency_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mycompany.currencytracker.common.Constants.image_url
import com.mycompany.currencytracker.domain.model.currency.fiat.FiatDetails
import com.mycompany.currencytracker.presentation.common.fiat.ChangeRate

import com.mycompany.currencytracker.presentation.ui.theme.mainTextColor
import com.mycompany.currencytracker.presentation.ui.theme.secondTextColor

val exampleFiatDetails = FiatDetails(
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
    fiatDetails: FiatDetails = exampleFiatDetails,
    currNumber: Int = 1,
    onItemClick: (FiatDetails) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable{onItemClick(fiatDetails)}
            .padding(20.dp)
            .height(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(0.9f),
            text = "$currNumber",
            style = MaterialTheme.typography.bodyLarge,
            color = secondTextColor
        )
        Row(modifier = Modifier.weight(2f)) {
            AsyncImage(
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .clip(CircleShape),
                model = image_url + fiatDetails.symbol.lowercase() + ".png",
                contentDescription = "image description",
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier.padding(start = 12.dp),
                text = fiatDetails.symbol,
                style = MaterialTheme.typography.bodyLarge,
                color = mainTextColor
            )
        }
        Text(
            modifier = Modifier
                .weight(3f)
                .padding(horizontal = 10.dp),
            text = when {
                fiatDetails.rate >= 1 -> "$${String.format("%.2f", fiatDetails.rate)}"
                fiatDetails.rate >= 0.0001 -> "$${String.format("%.4f", fiatDetails.rate)}"
                fiatDetails.rate >= 0.00000001 -> "$${String.format("%.8f", fiatDetails.rate)}"
                else -> "$0.00000000"
            },
            style = MaterialTheme.typography.bodyLarge,
            color = mainTextColor,
            textAlign = TextAlign.End
        )
        Row(
            horizontalArrangement = Arrangement.End
        ) {
            ChangeRate(fiatDetails = fiatDetails)
        }
    }
}

