package com.mycompany.currencytracker.presentation.common.currency.crypto

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.domain.model.currency.crypto.CryptoGeneralInfo
import com.mycompany.currencytracker.domain.use_case.crypto.GetTop100RateUseCase
import com.mycompany.currencytracker.presentation.common.asErrorUiText
import com.mycompany.currencytracker.presentation.common.list.IListViewModel
import com.mycompany.currencytracker.presentation.crypto_list.CryptoListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CryptoSearchListViewModel @Inject constructor(
    private val getTop100RateUseCase: GetTop100RateUseCase
) : ViewModel(), IListViewModel<CryptoGeneralInfo> {

    private val _state = mutableStateOf(CryptoListState())
    override val state: State<CryptoListState> = _state

    private val _searchResult = mutableStateOf<List<CryptoGeneralInfo>>(emptyList())
    val searchResult: State<List<CryptoGeneralInfo>> = _searchResult

    init {
        getItems()
    }

    override fun getItems(){
        getTop100RateUseCase().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    val cryptos = result.data
                    _state.value = CryptoListState(items = cryptos)
                    _searchResult.value = cryptos
                }
                is Resource.Error -> {
                    val msg = result.asErrorUiText()
                    _state.value = CryptoListState(error = msg)

                }
                is Resource.Loading -> {
                    _state.value = CryptoListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun search(query: String) {
        val filteredList = _state.value.items.filter { crypto ->
            crypto.name.contains(query, ignoreCase = true) || crypto.symbol.contains(query, ignoreCase = true)
        }
        _searchResult.value = filteredList
    }
}