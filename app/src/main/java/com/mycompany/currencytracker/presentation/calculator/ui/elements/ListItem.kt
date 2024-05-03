package com.mycompany.currencytracker.presentation.calculator.ui.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mycompany.currencytracker.common.Constants
import com.mycompany.currencytracker.domain.model.currency.crypto.CryptoGeneralInfo
import com.mycompany.currencytracker.domain.model.currency.fiat.FiatDetails
import java.util.Locale

@Composable
fun CryptoListItem(
    crypto: CryptoGeneralInfo,
    onItemClick: ()->Unit
){
    Row(
        modifier = Modifier
            .width(390.dp)
            .height(56.dp)
            .padding(start = 20.dp, bottom = 16.dp, top = 16.dp)
            .clickable {
                onItemClick()
            }
    ) {
        AsyncImage(
            modifier = Modifier
                .width(24.dp)
                .height(24.dp)
                .clip(CircleShape),
            model = crypto.image,
            contentDescription = "image description",
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier
                .padding(start = 12.dp)
                .width(79.dp),
            text = crypto.symbol.uppercase(Locale.getDefault()),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = crypto.name,
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 22.sp,
                fontWeight = FontWeight(400),
            ),
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun SimpleFiatListItem(
    fiat: FiatDetails,
    onItemClick: ()->Unit
){
    Row(
        modifier = Modifier
            .width(390.dp)
            .height(56.dp)
            .padding(start = 20.dp, bottom = 16.dp, top = 16.dp)
            .clickable {
                onItemClick()
            }
    ) {
        AsyncImage(
            modifier = Modifier
                .width(24.dp)
                .height(24.dp)
                .clip(CircleShape),
            model = Constants.IMAGE_URL + fiat.symbol.lowercase() + ".png",
            contentDescription = "image description",
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier
                .padding(start = 12.dp)
                .width(79.dp),
            text = fiat.symbol.uppercase(Locale.getDefault()),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = fiat.name,
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 22.sp,
                fontWeight = FontWeight(400),
                color = MaterialTheme.colorScheme.secondary
            )
        )
    }
}