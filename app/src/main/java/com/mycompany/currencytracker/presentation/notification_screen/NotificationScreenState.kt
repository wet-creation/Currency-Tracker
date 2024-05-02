package com.mycompany.currencytracker.presentation.notification_screen

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mycompany.currencytracker.domain.model.currency.crypto.CryptoGeneralInfo
import com.mycompany.currencytracker.presentation.common.UiText
import com.mycompany.currencytracker.presentation.common.emptyUiText

data class NotificationScreenState(
    val currentPriceForFiat: String = "0",
    val currentPriceForCrypto: String = "0",
    val symbolForFiat: String = "USD",
    val symbolForCrypto: String = "BTC",
    val fiatSelected: Boolean = true,
    val writtenPrice: String = "0",
    val error: UiText = emptyUiText,
    val crypto: CryptoGeneralInfo = CryptoGeneralInfo(),
    val isLoading: Boolean = false,
    val textSize: TextUnit = 48.sp,
    val textWidth: Dp = 0.dp,
)