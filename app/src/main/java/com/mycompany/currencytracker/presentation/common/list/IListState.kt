package com.mycompany.currencytracker.presentation.common.list

import com.mycompany.currencytracker.presentation.common.UiText


/**
 * Interface representing the state of a list.
 *
 * @param T The type of items in the list.
 */
interface  IListState<T> {
    /** Indicates whether the list is in a loading state. */
    val isLoading: Boolean
    /** The list of items. */
    val items: List<T>
    /** The error message, if any, associated with the list state. */
    val error: UiText
}