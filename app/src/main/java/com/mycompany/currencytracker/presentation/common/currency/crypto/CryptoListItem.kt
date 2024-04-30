package com.mycompany.currencytracker.presentation.common.currency.crypto

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.domain.model.currency.ICrypto
import com.mycompany.currencytracker.domain.model.currency.crypto.CryptoGeneralInfo
import com.mycompany.currencytracker.presentation.common.currency.fiat.ChangeRate
import com.mycompany.currencytracker.presentation.ui.theme.mainTextColor
import com.mycompany.currencytracker.presentation.ui.theme.secondTextColor


val cryptoTest = CryptoGeneralInfo(
    rate = 134342.0,
    image = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/46/Bitcoin.svg/225px-Bitcoin.svg.png",
    rank = 1,
    name = "Bitcoin",
    symbol = "btc",
    market_cap = 12121211,
    _24h = 12121.0,
    _7d = null,
    _30d = null,
    id = ""
)

@Preview
@Composable
fun CryptoListItem(
    crypto: ICrypto = cryptoTest,
    number: Int = 1,
    onItemClick: (crypto: ICrypto) -> Unit = {}
) {
    val context = LocalContext.current
    val dataStore = StoreUserSetting(context)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .height(43.dp)
            .clickable { onItemClick(crypto) }
            .testTag("CryptoItem"),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(0.9f),
            text = "$number",
            style = MaterialTheme.typography.bodyLarge,
            color = secondTextColor
        )
        Row(modifier = Modifier.weight(2.4f)) {
            Image(
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterVertically),
                painter = rememberAsyncImagePainter(model = crypto.image),
                contentDescription = "image description",
                contentScale = ContentScale.Crop
            )
            Column(
                verticalArrangement = Arrangement.spacedBy((-1).dp, Alignment.Top),
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
                    text = calculateDigit(crypto.market_cap!!),
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
                .padding(horizontal = 10.dp)
                .align(Alignment.CenterVertically),
            text = calculateDecimalPlaces(crypto.rate),
            style = MaterialTheme.typography.bodyLarge,
            color = mainTextColor,
            textAlign = TextAlign.End
        )
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.align(Alignment.CenterVertically),
        ) {
            ChangeRate(crypto, dataStore.getChartTime())
        }
    }
}
