package com.mycompany.currencytracker.presentation.common.crypto

import kotlin.math.floor

fun calculateDigit(number: Long): String {
    return when {
        number >= 1000000000000000 -> "${floor(number / 1000000000000000f * 100f) / 100f} q"
        number >= 1000000000000 -> "${floor(number / 1000000000000f * 100f) / 100f} T"
        number >= 1000000000 -> "${floor(number / 1000000000f * 100f) / 100f} B"
        number >= 1000000 -> "${floor(number / 1000000f * 100f) / 100f} M"
        else -> "$number"
    }
}

fun calculateDecimalPlaces(number: Double) : String{
    return when {
        number >= 1 -> String.format("%.2f", number)
        number >= 0.0001 -> String.format("%.4f", number)
        number >= 0.00000001 -> String.format("%.8f", number)
        number >= 0.00000000001 -> String.format("%.11f", number)
        else -> number.toString()
    }
}