package com.mycompany.currencytracker.presentation.common.crypto

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
import com.mycompany.currencytracker.domain.model.currency.crypto.CryptoGeneralInfo
import com.mycompany.currencytracker.presentation.common.emptyUiString
import com.mycompany.currencytracker.presentation.crypto_list.CryptoListViewModel
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoListScreen(
    haveHeader: Boolean,
    viewModel: ICryptoViewModel = hiltViewModel<CryptoListViewModel>(),
    itemContent: @Composable (
        currencyItem: CryptoGeneralInfo,
    ) -> Unit

) {
    val state = viewModel.state.value
    val list: MutableList<CryptoGeneralInfo> = state.cryptos.toMutableList()

    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = state.isLoading,
            onRefresh = { viewModel.getCrypto() }
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
                if (viewModel is CryptoListViewModel) {
                    itemsIndexed(list) { _, crypto ->
                        itemContent(crypto)
                    }
                } else if (viewModel is CryptoSearchListViewModel) {
                    itemsIndexed(viewModel.searchResult.value) { _, currency ->
                        itemContent(
                            currency
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
        if (state.error != emptyUiString) {
            Text(
                text = state.error.asString()
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