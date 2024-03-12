package com.mycompany.currencytracker.presentation.setting_screen.currencySelectScreen.crypto

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.domain.model.currency.crypto.CryptoDetails
import com.mycompany.currencytracker.domain.model.currency.fiat.Currency
import com.mycompany.currencytracker.domain.use_case.crypto.GetTop100RateUseCase
import com.mycompany.currencytracker.presentation.crypto_list.CryptoListState
import com.mycompany.currencytracker.presentation.currency_list.CurrencyListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CryptoSelectListViewModel @Inject constructor(
    private val getTop100RateUseCase: GetTop100RateUseCase
) : ViewModel() {

    private val _state = mutableStateOf(CryptoListState())
    val state: State<CryptoListState> = _state

    private val _searchResult = mutableStateOf<List<CryptoDetails>>(emptyList())
    val searchResult: State<List<CryptoDetails>> = _searchResult

    init {
        getCryptos()
    }

    private fun getCryptos(){
        getTop100RateUseCase().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    val cryptos = result.data ?: emptyList()
                    _state.value = CryptoListState(cryptos = cryptos)
                    _searchResult.value = cryptos
                }
                is Resource.Error -> {
                    _state.value = CryptoListState(
                        error = result.message ?: "an unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = CryptoListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun search(query: String) {
        val filteredList = _state.value.cryptos.filter { crypto ->
            crypto.name.contains(query, ignoreCase = true) || crypto.symbol.contains(query, ignoreCase = true)
        }
        _searchResult.value = filteredList
    }
}