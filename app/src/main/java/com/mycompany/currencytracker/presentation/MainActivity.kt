package com.mycompany.currencytracker.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mycompany.currencytracker.presentation.navigation.MainScreen
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