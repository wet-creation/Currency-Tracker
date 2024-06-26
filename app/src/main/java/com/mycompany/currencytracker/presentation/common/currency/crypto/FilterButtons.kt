package com.mycompany.currencytracker.presentation.common.currency.crypto

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import kotlinx.coroutines.launch

@Composable
fun FilterButtons(
    dataStore: StoreUserSetting,
    onClick: () -> Unit
) {
    val scope = rememberCoroutineScope()

    val isFiat by dataStore.isFiatSelected.collectAsState(initial = true)
    val chartTime by dataStore.getChartTime.collectAsState(initial = "24h")

    var showPopupMenu by remember {
        mutableStateOf(false)
    }

    val buttonText = if (isFiat) {
        "${dataStore.getFiat()} / ${dataStore.getCrypto().uppercase()}"
    } else {
        "${dataStore.getCrypto().uppercase()} / ${dataStore.getFiat()}"
    }

    Row(
        modifier = Modifier.padding(top = 19.dp, bottom = 10.dp, start = 20.dp)
    ) {
        Button(
            modifier = Modifier.size(width = 130.dp, height = 35.dp),
            onClick = {
                scope.launch {
                    dataStore.saveSelectViewCurrency(!isFiat)
                    onClick()
                }
            },
            content = {
                Text(text = buttonText)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.primaryContainer
            )
        )

        Box(
            modifier = Modifier.padding(start = 7.dp)
        ) {
            Button(
                onClick = { showPopupMenu = true },
                modifier = Modifier.size(width = 75.dp, height = 35.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(text = chartTime)
            }

            DropdownMenu(expanded = showPopupMenu, onDismissRequest = { showPopupMenu = false }) {
                DropdownMenuItem(text = { Text(text = "24h") }, onClick = {
                    scope.launch {
                        dataStore.saveChartTime("24h")
                        showPopupMenu = false
                        onClick()
                    }
                })
                DropdownMenuItem(text = { Text(text = "7d") }, onClick = {
                    scope.launch {
                        dataStore.saveChartTime("7d")
                        showPopupMenu = false
                        onClick()
                    }
                })
                DropdownMenuItem(text = { Text(text = "30d") }, onClick = {
                    scope.launch {
                        dataStore.saveChartTime("30d")
                        showPopupMenu = false
                        onClick()
                    }
                })
            }
        }
    }
}