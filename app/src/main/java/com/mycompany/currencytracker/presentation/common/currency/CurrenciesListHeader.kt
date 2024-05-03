package com.mycompany.currencytracker.presentation.common.currency

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.presentation.common.currency.crypto.FilterButtons


@Composable
fun CurrenciesListHeader(
    dataStore: StoreUserSetting,
    hasFilter: Boolean = false,
    onClick: () -> Unit = {}
) {
    val getChartTime by dataStore.getChartTime.collectAsState(initial = "24h")

    if (hasFilter) {
        FilterButtons(dataStore, onClick)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp)
            .padding(start = 20.dp, end = 20.dp, top = 15.dp)
    ) {
        Text(
            modifier = Modifier.weight(0.9f),
            text = "#",
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            modifier = Modifier.weight(2f),
            textAlign = TextAlign.Start,
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
            modifier = Modifier.weight(2f),
            text = getChartTime,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.labelSmall
        )
    }
}
