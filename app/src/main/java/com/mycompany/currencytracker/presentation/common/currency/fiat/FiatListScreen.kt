package com.mycompany.currencytracker.presentation.common.currency.fiat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.domain.model.currency.fiat.FiatDetails
import com.mycompany.currencytracker.presentation.currency_list.CurrencyListViewModel
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiatListScreen(
    haveHeader: Boolean,
    viewModel: IFiatViewModel = hiltViewModel<CurrencyListViewModel>(),
    itemContent: @Composable (
        currencyItem: FiatDetails,
        currNumber: Int
    ) -> Unit,
) {

    val state = viewModel.state.value
    val list = state.currencies

    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = state.isLoading,
            onRefresh = { viewModel.getCurrencies() }
        )


    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        Column {
            if (haveHeader) {
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
            LazyColumn(modifier = Modifier.fillMaxSize()) {

                if (viewModel is CurrencyListViewModel) {
                    itemsIndexed(list) { currNumber, currency ->
                        itemContent(
                            currency,
                            currNumber + 1
                        )
                    }
                } else if (viewModel is FiatSearchListViewModel) {
                    itemsIndexed(viewModel.searchResult.value) { currNumber, currency ->
                        itemContent(
                            currency,
                            currNumber + 1
                        )
                    }
                }


            }

        }
        PullRefreshIndicator(
            refreshing = state.isLoading,
            state = pullRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
        if (state.error.isNotBlank()) {
            Text(
                text = state.error
            )
            PullRefreshIndicator(
                refreshing = state.isLoading,
                state = pullRefreshState,
                modifier = Modifier
                    .align(Alignment.TopCenter)
            )
        }
    }
}