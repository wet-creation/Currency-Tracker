package com.mycompany.currencytracker.presentation.common.currency

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.presentation.common.search.SearchBar
import com.mycompany.currencytracker.presentation.common.search.SearchPosition


/**
 * Displays a list screen with tabs for switching between fiat and crypto currency views.
 * The screen can optionally include a search bar at the top or between the tab row and content.
 *
 * @param searchPosition Specifies the position of the search bar on the screen. It can be at the top,
 *                       in between the tabs and content, or not present at all. Defaults to None if not specified.
 * @param currencyListSearchState Holds the state for the list screen, including the current text in the search bar and
 *                        an action to be performed when the text changes. Defaults to an empty state if not specified.
 * @param fiatListScreen A composable function to render the list of fiat currencies. This is displayed when the fiat tab is selected.
 * @param cryptoListScreen A composable function to render the list of crypto currencies. This is displayed when the crypto tab is selected.
 * @param tabContent The composable function to customize the appearance of the tab titles.
 */
@Composable
fun CurrencyListsScreen(
    modifier: Modifier = Modifier,
    searchPosition: SearchPosition = SearchPosition.None,
    currencyListSearchState: CurrencyListSearchState = CurrencyListSearchState(),
    fiatListScreen: @Composable () -> Unit,
    cryptoListScreen: @Composable () -> Unit,
    tabContent: @Composable (title: String) -> Unit,
) {
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf(stringResource(id = R.string.fiat), stringResource(id = R.string.crypto))

    Column(modifier = modifier.fillMaxSize()) {
        if (searchPosition == SearchPosition.Top) {
            Row(
                modifier = Modifier.padding(20.dp)
            ) {
                SearchBar(currencyListSearchState)
            }

        }
        TabRow(
            modifier = Modifier
                .size(height = 22.dp, width = 200.dp)
                .padding(start = 20.dp),
            selectedTabIndex = tabIndex
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    modifier = Modifier.height(height = 22.dp),
                    text = {
                        tabContent(title)
                    },
                    selected = tabIndex == index,
                    onClick = {
                        tabIndex = index

                    }
                )
            }
        }
        if (searchPosition == SearchPosition.InBetween) {
            Row(
                modifier = Modifier.padding(20.dp)
            ) {
                SearchBar(currencyListSearchState)
            }
        }
        HorizontalDivider()
        when (tabIndex) {
            0 -> fiatListScreen()
            1 -> cryptoListScreen()
        }
    }

}

