package com.mycompany.currencytracker.presentation.common.currency

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mycompany.currencytracker.common.debugLog
import com.mycompany.currencytracker.presentation.ui.theme.selectTextColor

@Composable
fun ListScreen(
    fiatListScreen: @Composable () -> Unit,
    cryptoListScreen: @Composable () -> Unit,
    tabContent: @Composable (title: String) -> Unit,
) {
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Fiat", "Crypto")

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            modifier = Modifier
                .size(height = 22.dp, width = 106.dp)
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
                    onClick = { tabIndex = index
                        debugLog("tabIndex1 = $tabIndex")
                    }
                )
            }
        }
        HorizontalDivider()
        when (tabIndex) {
            0 -> fiatListScreen()
            1 -> cryptoListScreen()
        }
    }

}

@Composable
fun MyTab(title: String, tabIndexInput: Int, index: Int) {
    var tabIndex = tabIndexInput
    Tab(
        modifier = Modifier.height(height = 22.dp),
        text = {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = selectTextColor,
                )
            )
        },
        selected = true,
        onClick = {
        debugLog("tabIndex1 = $tabIndex")
        }
    )
}