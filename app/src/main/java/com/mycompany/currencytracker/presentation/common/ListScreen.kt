package com.mycompany.currencytracker.presentation.common

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

@Composable
fun ListScreen(
    searchPosition: SearchPosition = SearchPosition.None,
    stateListScreen: StateListScreen = StateListScreen(),
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
                SearchBar(searchText = stateListScreen.inputText) {
                    stateListScreen.inputTextAction(it)
                }
            }

        }
        TabRow(
            modifier = Modifier
                .size(height = 22.dp, width = 180.dp)
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
                SearchBar(searchText = stateListScreen.inputText) {
                    stateListScreen.inputTextAction(it)
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

