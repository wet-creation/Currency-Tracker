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
 * @param currencyListsScreenState Holds the state for the list screen, including the current text in the search bar and
 *                        an action to be performed when the text changes. Defaults to an empty state if not specified.
 * @param fiatListScreen A composable function to render the list of fiat currencies. This is displayed when the fiat tab is selected.
 * @param cryptoListScreen A composable function to render the list of crypto currencies. This is displayed when the crypto tab is selected.
 * @param tabContent A composable function to render the content of each tab. It takes a title (String) as its parameter,
 *                   which is the title of the tab.
 *
 * The screen layout includes a column that may start with a search bar (if specified by searchPosition),
 * followed by a row of tabs for selecting between fiat and crypto currencies, and finally the content for
 * the currently selected tab.
 *
 * The tabs are dynamically generated based on a list of titles (currently "Fiat" and "Crypto"), which are
 * obtained from string resources. The currently selected tab is highlighted, and selecting a different tab
 * updates the content displayed below the tabs.
 *
 * If the search bar is positioned in between the tabs and content, it appears after the tabs and before the
 * content corresponding to the selected tab. The search bar allows the user to filter or search the list of
 * currencies displayed in the currently selected tab.
 *
 * A horizontal divider separates the tabs (and optional search bar) from the content of the selected tab.
 */
@Composable
fun CurrencyListsScreen(
    searchPosition: SearchPosition = SearchPosition.None,
    currencyListsScreenState: CurrencyListsScreenState = CurrencyListsScreenState(),
    fiatListScreen: @Composable () -> Unit,
    cryptoListScreen: @Composable () -> Unit,
    tabContent: @Composable (title: String) -> Unit,
) {
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf(stringResource(id = R.string.fiat), stringResource(id = R.string.crypto))

    Column(modifier = Modifier.fillMaxSize()) {
        if (searchPosition == SearchPosition.Top) {
            Row(
                modifier = Modifier.padding(20.dp)
            ) {
                SearchBar(searchText = currencyListsScreenState.inputText) {
                    currencyListsScreenState.inputTextAction(it)
                }
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
                SearchBar(searchText = currencyListsScreenState.inputText) {
                    currencyListsScreenState.inputTextAction(it)
                }
            }
        }
        HorizontalDivider()
        when (tabIndex) {
            0 -> fiatListScreen()
            1 -> cryptoListScreen()
        }
    }

}

