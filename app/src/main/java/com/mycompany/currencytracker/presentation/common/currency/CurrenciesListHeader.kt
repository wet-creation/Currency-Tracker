package com.mycompany.currencytracker.presentation.common.currency

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mycompany.currencytracker.R

@Composable
fun CurrenciesListHeader() {
    Row(
        modifier = Modifier
            .width(349.dp)
            .height(42.dp)
            .padding(start = 20.dp, end = 13.dp, top = 15.dp)
    ) {
        Text(
            modifier = Modifier.weight(0.9f),
            text = "#",
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            modifier = Modifier.weight(2f),
            text = stringResource(R.string.list_column_name),
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            modifier = Modifier.weight(3f),
            text = stringResource(R.string.list_column_price),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            modifier = Modifier.weight(1.8f),
            text = stringResource(R.string.list_column_change),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.labelSmall
        )
    }
}
