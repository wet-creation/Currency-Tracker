package com.mycompany.currencytracker.presentation.common.list

import androidx.compose.runtime.State

interface IListViewModel<T> {
    val state: State<IListState<T>>
    fun getItems()
}

