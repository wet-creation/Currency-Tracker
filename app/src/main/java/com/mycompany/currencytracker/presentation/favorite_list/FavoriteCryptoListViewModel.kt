package com.mycompany.currencytracker.presentation.favorite_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mycompany.currencytracker.domain.model.currency.crypto.CryptoGeneralInfo
import com.mycompany.currencytracker.presentation.common.list.IListViewModel
import com.mycompany.currencytracker.presentation.crypto_list.CryptoListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteCryptoListViewModel @Inject constructor(

) : ViewModel(), IListViewModel<CryptoGeneralInfo> {

    val _state = mutableStateOf(CryptoListState())
    override val state: State<CryptoListState> = _state
    override fun getItems() {
        TODO("Not yet implemented")
    }


}