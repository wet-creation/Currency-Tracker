package com.mycompany.currencytracker.presentation.calculator.ui.elements

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mycompany.currencytracker.common.debugLog
import com.mycompany.currencytracker.presentation.calculator.states.RowStateCalculator

@Composable
fun ConvertItem(
    rowStateCalculator: RowStateCalculator,
    contentDescription: String
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clickable {
                    debugLog("Change currency")
                }
        ) {
            AsyncImage(
                model = rowStateCalculator.image,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .clip(CircleShape)
            )
            Text(
                text = rowStateCalculator.symbol,
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .weight(3f)
                .fillMaxWidth()
                .clickable {
                    debugLog("Select value")
                }
        ) {
//            BasicTextField(
//                value = rowStateCalculator.sum,
//                textStyle = MaterialTheme.typography.displayLarge,
//                onValueChange = {},
//                singleLine = true,
//
//            )
            Text(
                text = rowStateCalculator.sum,
                style = MaterialTheme.typography.displayLarge,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = rowStateCalculator.name,
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
