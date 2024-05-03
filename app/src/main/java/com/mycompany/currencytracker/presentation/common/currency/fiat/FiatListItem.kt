package com.mycompany.currencytracker.presentation.common.currency.fiat

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
import com.mycompany.currencytracker.common.Constants.IMAGE_URL
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.domain.model.currency.IFiat
import com.mycompany.currencytracker.presentation.common.AutoResizedText
import com.mycompany.currencytracker.presentation.common.currency.ChangeRateTable
import com.mycompany.currencytracker.presentation.common.currency.crypto.calculateDecimalPlaces


@Composable
fun FiatListItem(
    fiatDetails: IFiat,
    currNumber: Int = 1,
    dataStore: StoreUserSetting,
    onItemClick: (IFiat) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(fiatDetails) }
            .padding(vertical = 14.dp, horizontal = 20.dp)
            .height(24.dp),
    ) {
        Text(
            modifier = Modifier
                .weight(0.9f)
                .fillMaxWidth(),
            text = "$currNumber",
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )
        Row(
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth()
        ) {
            AsyncImage(
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth(),
                model = IMAGE_URL + fiatDetails.symbol.lowercase() + ".png",
                contentDescription = "image description",
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .fillMaxWidth(),
                text = fiatDetails.symbol,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
        AutoResizedText(
            modifier = Modifier
                .weight(3f)
                .fillMaxWidth(),
            text = dataStore.getFiat() + " " + calculateDecimalPlaces(fiatDetails.rate),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.End
        )
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f),
        ) {
            ChangeRateTable(currencyRate = fiatDetails)
        }
    }
}