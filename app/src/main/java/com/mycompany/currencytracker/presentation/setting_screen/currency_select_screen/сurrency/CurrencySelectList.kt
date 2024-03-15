package com.mycompany.currencytracker.presentation.setting_screen.currency_select_screen.сurrency

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mycompany.currencytracker.domain.model.currency.fiat.FiatDetails
import com.mycompany.currencytracker.presentation.common.currency.SearchBar
import com.mycompany.currencytracker.presentation.common.currency.fiat.FiatSearchListViewModel

@Composable
fun CurrencySelectList(
    viewModel: FiatSearchListViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    val searchResult: MutableList<FiatDetails> = viewModel.searchResult.value.toMutableList()
    searchResult.removeIf { it.symbol == "BTC" }

    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier.padding(20.dp)
        ) {
            SearchBar(searchText = searchText) {
                searchText = it
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(searchResult) { currency ->
                CurrencySelectListItem(fiatDetails = currency) {

                }
            }
        }

        if (state.error.isNotBlank()) {
            Text(
                text = state.error
            )
        }
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }

    LaunchedEffect(searchText) {
        viewModel.search(searchText)
    }
}