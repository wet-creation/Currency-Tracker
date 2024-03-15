package com.mycompany.currencytracker.presentation.crypto_list

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
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.domain.model.currency.crypto.CryptoGeneralInfo
import com.mycompany.currencytracker.presentation.crypto_list.components.CryptoListItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoListScreen(
    viewModel: CryptoListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val list: MutableList<CryptoGeneralInfo> = state.cryptos.toMutableList()

    val pullRefreshState = rememberPullToRefreshState()
    LaunchedEffect(state.isLoading) {
        if (state.isLoading)
            pullRefreshState.startRefresh()
        else
            pullRefreshState.endRefresh()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(pullRefreshState.nestedScrollConnection)
    ) {
        Column {
            Row (
                modifier = Modifier
                    .width(349.dp)
                    .height(42.dp)
                    .padding(start = 20.dp, end = 13.dp, top = 15.dp)
            ){
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
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(list) { number, crypto ->
                    CryptoListItem(crypto = crypto, number = number + 1) {}
                }

            }
        }
        PullToRefreshContainer(
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        if (state.error.isNotBlank()) {
            Text(
                text = state.error
            )
            PullToRefreshContainer(
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}