package com.mycompany.currencytracker.presentation.favorite_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mycompany.currencytracker.domain.model.currency.fiat.FiatDetails
import com.mycompany.currencytracker.presentation.common.list.IListViewModel
import com.mycompany.currencytracker.presentation.currency_list.CurrencyListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteFiatListViewModel @Inject constructor(

) : ViewModel(), IListViewModel<FiatDetails> {

    val _state = mutableStateOf(CurrencyListState())
    override val state: State<CurrencyListState> = _state

    override fun getItems() {
        TODO("Not yet implemented")
    }
}