package com.mycompany.currencytracker.presentation.currency_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.mycompany.currencytracker.domain.model.currency.Currency
import com.mycompany.currencytracker.presentation.currency_list.components.CurrencyListItem
import com.mycompany.currencytracker.presentation.ui.theme.darkBackgroundColor

@Composable
fun CurrencyListScreen(
    viewModel: CurrencyListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val list: MutableList<Currency> = state.currencies.toMutableList()
    list.removeIf { it.symbol == "BTC"}

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackgroundColor)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(list) { curr_number, currency ->
                CurrencyListItem(currency = currency, curr_number = curr_number + 1)
            }
        }
        if (state.error.isNotBlank()) {
            Text(
                text = state.error
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}