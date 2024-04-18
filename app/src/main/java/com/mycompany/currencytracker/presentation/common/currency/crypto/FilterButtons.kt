package com.mycompany.currencytracker.presentation.common.currency.crypto

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import kotlinx.coroutines.launch

@Composable
fun FilterButtons(
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val dataStore = StoreUserSetting(context)
    val scope = rememberCoroutineScope()

    val isFiat = dataStore.getIsFiatSelected()

    val buttonText = if(isFiat){
        "${dataStore.getFiat()} / ${dataStore.getCrypto().uppercase()}"
    }
    else{
        "${dataStore.getCrypto().uppercase()} / ${dataStore.getFiat()}"
    }

    Row() {
        Button(
            onClick = {
                scope.launch {
                    dataStore.saveSelectViewCurrency(!isFiat)
                    onClick()
                }
            },
            content = {
                Text(text = buttonText)
            }
        )
    }
}