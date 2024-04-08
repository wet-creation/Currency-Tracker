package com.mycompany.currencytracker.presentation.common.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mycompany.currencytracker.presentation.common.emptyUiText
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ItemsListScreen(
    header: @Composable () -> Unit = {},
    viewModel: IListViewModel<T>,
    list: List<T>,
    itemContent: @Composable (
        currencyItem: T,
        currentNumber: Int
    ) -> Unit
) {
    val state = viewModel.state.value
    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = state.isLoading,
            onRefresh = { viewModel.getItems() }
        )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        Column {
            header()
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(list) { index, crypto ->
                    itemContent(crypto, index + 1)
                }
            }

        }
        PullRefreshIndicator(
            refreshing = state.isLoading,
            state = pullRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter)

        )
        if (state.error != emptyUiText) {
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
