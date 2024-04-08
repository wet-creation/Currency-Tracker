package com.mycompany.currencytracker.presentation.common.list

import com.mycompany.currencytracker.presentation.common.UiText

interface IListState<T> {
    val isLoading: Boolean
    val currencies: List<T>
    val error: UiText
}