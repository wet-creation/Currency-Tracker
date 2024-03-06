package com.mycompany.currencytracker.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.datastore.core.DataStore
import androidx.preference.Preference
import com.mycompany.currencytracker.presentation.currency_list.CurrencyListScreen
import com.mycompany.currencytracker.presentation.ui.theme.CurrencyTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CurrencyTrackerTheme {
                MainScreen()
            }
        }
    }
}