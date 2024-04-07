package com.mycompany.currencytracker.presentation.common.crypto

import androidx.compose.runtime.State
import com.mycompany.currencytracker.presentation.crypto_list.CryptoListState

interface ICryptoViewModel {
    val state: State<CryptoListState>
    fun getCrypto()
}