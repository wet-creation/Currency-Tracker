package com.mycompany.currencytracker.presentation.common.currency


/**
 * Data class representing the state of the currency lists screen.
 *
 * @param inputText The current text entered in the search bar.
 * @param inputTextAction Callback function to handle changes in the search bar text.
 */
data class CurrencyListsScreenState (
    val inputText: String = "",
    val inputTextAction: (String) -> Unit = { }
)